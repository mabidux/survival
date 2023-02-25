package dev.abidux.survive.event.skills.plantar;

import dev.abidux.survive.Main;
import dev.abidux.survive.manager.PlayerSkill;
import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.Skills;
import dev.abidux.survive.model.skills.skill.CappedSkill;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class HarvestEvent implements Listener {

    @EventHandler
    void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockData data = block.getBlockData();
        if (!(data instanceof Ageable ageable) || ageable.getAge() != ageable.getMaximumAge()) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().toString().endsWith("_HOE")) return;

        CappedSkill type = Skills.PLANTAR;
        PlayerStats stats = PlayerStats.get(player);
        PlayerSkill skill = stats.getSkillSet().get(type);

        Damageable meta = (Damageable) item.getItemMeta();
        meta.setDamage(meta.getDamage() + 1);
        int level = skill.getLevel();
        if (level >= 2) {
            int chance = (level - 1) * 5;
            int r = Main.random.nextInt(100);
            if (r < chance) {
                meta.setDamage(meta.getDamage());
            }
            if (level >= 7) {
                int bonusFortune = level - 6;
                int bonus = Main.random.nextInt(bonusFortune + 1);

                ItemStack stack = block.getDrops().stream().filter(i -> i.getType() != Material.WHEAT_SEEDS && i.getType() != Material.BEETROOT_SEEDS).findFirst().orElse(null);
                if (stack != null) {
                    stack.setAmount(bonus);
                    block.getWorld().dropItem(block.getLocation(), stack);
                }
            }
        }
        item.setItemMeta(meta);
        if (meta.getDamage() >= item.getType().getMaxDurability()) {
            player.getInventory().setItemInMainHand(null);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
        }

        skill.addXp(player, 1);
    }

}