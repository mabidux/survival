package dev.abidux.survival.event.skills.plantar;

import dev.abidux.survival.Main;
import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.manager.Skills;
import dev.abidux.survival.model.skills.skill.CappedSkill;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class CreateFarmlandEvent implements Listener {

    @EventHandler
    void createFarmland(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.FARMLAND) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().toString().endsWith("_HOE")) return;

        SkillSet set = SkillManager.get(player);
        CappedSkill type = Skills.PLANTAR;
        PlayerSkill skill = set.get(type);

        Damageable meta = (Damageable) item.getItemMeta();
        int level = skill.getLevel();
        if (level == 0) {
            meta.setDamage(meta.getDamage() + 1);
        } else if (level >= 2) {
            int chance = (level - 1) * 5;
            int r = Main.random.nextInt(100);
            if (r < chance) {
                meta.setDamage(meta.getDamage() - 1);
            }
        }
        if (meta.getDamage() >= item.getType().getMaxDurability()) {
            player.getInventory().setItemInMainHand(null);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
        }
        item.setItemMeta(meta);

        skill.addXp(player, 1);
    }
}