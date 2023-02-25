package dev.abidux.survive.event.skills.minerar;

import dev.abidux.survive.Main;
import dev.abidux.survive.manager.skills.PlayerSkill;
import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.skills.Skills;
import dev.abidux.survive.model.skills.skill.CappedSkill;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class MineBlockEvent implements Listener {

    @EventHandler
    void mine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().toString().endsWith("PICKAXE")) return;

        Block block = event.getBlock();
        int xp = getBlockXP(block.getType(), item);
        if (xp == 0) return;

        CappedSkill type = Skills.MINERAR;
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
            if (level >= 7) {
                if (!block.getType().toString().endsWith("ORE")) return;

                int bonusFortune = level - 6;
                int bonus = Main.random.nextInt(bonusFortune + 1);
                ItemStack stack = block.getDrops().iterator().next().clone();
                stack.setAmount(bonus);
                block.getWorld().dropItem(block.getLocation(), stack);
            }
        }

        skill.addXp(player, xp);
    }

    private int getBlockXP(Material material, ItemStack pickaxe) {
        if (pickaxe.containsEnchantment(Enchantment.SILK_TOUCH)) return 1;
        return switch(material) {
            case STONE, ANDESITE, DIORITE, GRANITE, DEEPSLATE, TUFF -> 1;
            case COAL_ORE, IRON_ORE, COPPER_ORE, NETHER_QUARTZ_ORE, NETHER_GOLD_ORE, DEEPSLATE_COAL_ORE, DEEPSLATE_IRON_ORE, DEEPSLATE_COPPER_ORE -> 2;
            case GOLD_ORE, REDSTONE_ORE, LAPIS_ORE, DEEPSLATE_GOLD_ORE, DEEPSLATE_REDSTONE_ORE, DEEPSLATE_LAPIS_ORE, OBSIDIAN -> 3;
            case DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_DIAMOND_ORE, DEEPSLATE_EMERALD_ORE -> 5;
            default -> 0;
        };
    }

}