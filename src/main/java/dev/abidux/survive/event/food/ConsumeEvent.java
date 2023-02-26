package dev.abidux.survive.event.food;

import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.food.FoodSystem;
import dev.abidux.survive.model.food.FoodSickness;
import dev.abidux.survive.util.chat.MaterialUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ConsumeEvent implements Listener {

    @EventHandler
    void consume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Material type = item.getType();
        if (!type.isEdible()) return;
        Player player = event.getPlayer();

        FoodSystem foodSystem = PlayerStats.get(player).getFoodSystem();
        FoodSickness sickness = Optional.ofNullable(foodSystem.getSicknessOf(type)).orElseGet(() -> new FoodSickness(0, 0));
        boolean aumentou = sickness.incrementSickness();
        foodSystem.addSickness(type, sickness);
        if (aumentou) {
            player.sendMessage("§c" + MaterialUtil.prettifyMaterial(type) + " não está tão bom quanto costumava ser...");
        }
        player.setFoodLevel(player.getFoodLevel() - sickness.getLevel());
    }

}