package dev.abidux.survive.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;

public class PurifiedWaterRecipe extends FurnaceRecipe {

    public PurifiedWaterRecipe() {
        super(NamespacedKey.fromString("survive.purified_water"), CustomItems.PURIFIED_WATER, Material.POTION, 0, 200);
    }
}