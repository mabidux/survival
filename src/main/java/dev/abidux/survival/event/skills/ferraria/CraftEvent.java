package dev.abidux.survival.event.skills.ferraria;

import dev.abidux.survival.enums.SkillType;
import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Arrays;
import java.util.Optional;

public class CraftEvent implements Listener {

    @EventHandler
    void craft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        Tool tool = new Tool(result);
        if (tool.isTool) {
            Player player = (Player) event.getViewers().get(0);
            SkillSet set = SkillManager.get(player);
            PlayerSkill skill = set.get(SkillType.FERRARIA);

            float percentage = (skill.level + 1) * .1f;
            Damageable meta = (Damageable) result.getItemMeta();

            int totalDurability = result.getType().getMaxDurability() - meta.getDamage();
            float adjustedTotalDurability = totalDurability * percentage;
            int adjustedDamage = (int) (result.getType().getMaxDurability() - adjustedTotalDurability);

            meta.setDamage(adjustedDamage);
            result.setItemMeta(meta);
        }
    }

    @EventHandler
    void crafted(CraftItemEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        Tool tool = new Tool(result);
        if (tool.isTool) {
            Player player = (Player) event.getWhoClicked();
            SkillSet set = SkillManager.get(player);
            SkillType type = SkillType.FERRARIA;
            PlayerSkill skill = set.get(type);
            int evolve = type.levels[skill.level];
            if (evolve == -1) return;

            Optional<Integer> opt = Arrays.stream(player.getInventory().getContents()).filter(item -> item == null || item.isSimilar(result))
                    .map(item -> item == null ? result.getMaxStackSize() : result.getMaxStackSize() - item.getAmount())
                    .reduce(Integer::sum);
            if (opt.isEmpty()) return;
            Optional<Integer> leastAmount = Arrays.stream(inventory.getMatrix()).map(i -> i == null ? 127 : i.getAmount()).min((a, b) -> a < b ? -1 : 1);
            int least;
            if (leastAmount.isEmpty() || (least = leastAmount.get()) == 127) return;
            int count = Math.min(opt.get(), least);
            int earned = tool.type.xp * count;
            skill.xp += earned;
            if (skill.xp >= evolve) {
                skill.xp = 0;
                skill.level++;
                player.sendMessage("§6+1 LEVEL §7- " + type.getName());
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            } else player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(new TextComponent("§b" + skill.xp + "/" + evolve + " XP §7- "), type.getComponent()));
        }
    }

    static class Tool {
        private static final String[] TOOLS_SUFFIX = {"PICKAXE", "AXE", "SWORD", "SHOVEL", "HOE"};
        private boolean isTool;
        private ToolType type;
        public Tool(ItemStack stack) {
            isTool = stack != null && isTool(stack);
            type = getToolType(stack);
        }

        private static ToolType getToolType(ItemStack stack) {
            if (stack == null) return null;
            return switch (stack.getType()) {
                case WOODEN_PICKAXE, WOODEN_AXE, WOODEN_SWORD, WOODEN_HOE, WOODEN_SHOVEL -> ToolType.WOODEN;
                case STONE_PICKAXE, STONE_AXE, STONE_SWORD, STONE_HOE, STONE_SHOVEL -> ToolType.STONE;
                case GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_SWORD, GOLDEN_HOE, GOLDEN_SHOVEL -> ToolType.GOLDEN;
                case IRON_PICKAXE, IRON_AXE, IRON_SWORD, IRON_HOE, IRON_SHOVEL -> ToolType.IRON;
                case DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_SWORD, DIAMOND_HOE, DIAMOND_SHOVEL -> ToolType.DIAMOND;
                case NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_SWORD, NETHERITE_HOE, NETHERITE_SHOVEL -> ToolType.NETHERITE;
                default -> null;
            };
        }

        private static boolean isTool(ItemStack stack) {
            String name = stack.getType().toString();
            for (String suffix : TOOLS_SUFFIX) {
                if (name.endsWith(suffix)) return true;
            }
            return false;
        }
    }

    enum ToolType {
        WOODEN(1),
        STONE(2),
        GOLDEN(2),
        IRON(4),
        DIAMOND(5),
        NETHERITE(10);

        public final int xp;
        ToolType(int xp) {
            this.xp = xp;
        }
    }

}