package dev.abidux.survival.event.skills.serraria;

import dev.abidux.survival.Main;
import dev.abidux.survival.enums.SkillType;
import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
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

        SkillSet set = SkillManager.get(player);
        SkillType type = SkillType.SERRARIA;
        PlayerSkill skill = set.get(type);

        if (skill.level == 0) {
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(meta.getDamage() + 1);
            item.setItemMeta(meta);
        } else if (skill.level >= 2) {
            int chance = (skill.level - 1) * 5;
            int r = Main.random.nextInt(100);
            if (r < chance) {
                Damageable meta = (Damageable) item.getItemMeta();
                meta.setDamage(meta.getDamage() - 1);
                item.setItemMeta(meta);
            }
        }

        int evolve = type.levels[skill.level];
        if (evolve == -1) return;
        skill.xp += xp;
        if (skill.xp >= evolve) {
            skill.xp = 0;
            skill.level++;
            player.sendMessage("§6+1 LEVEL §7- " + type.getName());
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            TextComponent component = new TextComponent(new TextComponent("§b" + skill.xp + "/" + evolve + " XP §7- "), type.getComponent());
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }

    private int getBlockXP(Material material) {
        return switch(material) {
            case OAK_LOG, ACACIA_LOG, BIRCH_LOG, DARK_OAK_LOG, SPRUCE_LOG, JUNGLE_LOG, MANGROVE_LOG -> 1;
            default -> 0;
        };
    }

}