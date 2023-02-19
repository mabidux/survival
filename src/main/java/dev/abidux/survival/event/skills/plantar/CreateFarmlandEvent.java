package dev.abidux.survival.event.skills.plantar;

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
        SkillType type = SkillType.PLANTAR;
        PlayerSkill skill = set.get(type);

        Damageable meta = (Damageable) item.getItemMeta();
        if (skill.level == 0) {
            meta.setDamage(meta.getDamage() + 1);
        } else if (skill.level >= 2) {
            int chance = (skill.level - 1) * 5;
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

        int evolve = type.levels[skill.level];
        if (evolve == -1) return;
        skill.xp++;
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

}