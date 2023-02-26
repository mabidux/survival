package dev.abidux.survive.event.skills.serraria;

import dev.abidux.survive.Main;
import dev.abidux.survive.model.skills.PlayerSkill;
import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.skills.Skills;
import dev.abidux.survive.model.skills.skill.CappedSkill;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class BreakLogEvent implements Listener {

    @EventHandler
    void breakLog(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().toString().endsWith("_AXE")) return;

        Block block = event.getBlock();
        int xp = getBlockXP(block.getType());
        if (xp == 0) return;

        CappedSkill type = Skills.SERRARIA;
        PlayerStats stats = PlayerStats.get(player);
        PlayerSkill skill = stats.getSkillSet().get(type);

        int level = skill.getLevel();
        if (level == 0) {
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(meta.getDamage() + 1);
            item.setItemMeta(meta);
        } else if (level >= 2) {
            int chance = (level - 1) * 5;
            int r = Main.random.nextInt(100);
            if (r < chance) {
                Damageable meta = (Damageable) item.getItemMeta();
                meta.setDamage(meta.getDamage() - 1);
                item.setItemMeta(meta);
            }
        }

        skill.addXp(player, xp);
    }

    private int getBlockXP(Material material) {
        return switch(material) {
            case OAK_LOG, ACACIA_LOG, BIRCH_LOG, DARK_OAK_LOG, SPRUCE_LOG, JUNGLE_LOG, MANGROVE_LOG -> 1;
            default -> 0;
        };
    }

}