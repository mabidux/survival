package dev.abidux.survive.model.skills;

import org.bukkit.inventory.ItemStack;

public class Tool {

    private static final String[] TOOLS_SUFFIX = {"PICKAXE", "AXE", "SWORD", "SHOVEL", "HOE"};
    public final boolean isTool;
    public final ToolType type;
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

    public enum ToolType {
        WOODEN(1),
        STONE(2),
        GOLDEN(2),
        IRON(4),
        DIAMOND(5);

        public final int xp;
        ToolType(int xp) {
            this.xp = xp;
        }
    }

}