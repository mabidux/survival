package dev.abidux.survive.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;

public class SaddleRecipe extends ShapedRecipe {
    public SaddleRecipe() {
        super(NamespacedKey.fromString("survive.saddle"), new ItemStack(Material.SADDLE));
        shape("ccc", "cfc", "f f");
        setIngredient('c', Material.LEATHER);
        setIngredient('f', Material.IRON_INGOT);
        setCategory(CraftingBookCategory.EQUIPMENT);
    }
}