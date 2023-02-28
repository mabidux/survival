package dev.abidux.survive.event.food;

import dev.abidux.survive.Main;
import dev.abidux.survive.manager.ActionbarManager;
import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.food.ThirstSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConsumeWaterEvent implements Listener {

    @EventHandler
    void consume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Material type = item.getType();
        if (type != Material.POTION) return;
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta.getCustomEffects().size() > 0) return;

        PersistentDataContainer nbt = meta.getPersistentDataContainer();
        byte data = nbt.getOrDefault(NamespacedKey.fromString("survive.is_purified"), PersistentDataType.BYTE, (byte)0);
        consumeWater(event.getPlayer(), data == 1);
    }

    private void consumeWater(Player player, boolean purified) {
        PlayerStats stats = PlayerStats.get(player);
        ThirstSystem thirstSystem = stats.getThirstSystem();
        int newThirst = thirstSystem.getThirst() + (purified ? 6 : 3);
        thirstSystem.setThirst(newThirst);
        if (!purified && Main.random.nextInt(100) < 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 30, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 1));
        }
        ActionbarManager.update(player, true);
    }

}