package dev.abidux.survive.recipe;

import dev.abidux.survive.util.inventory.ItemCreator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomItems {

    public static final ItemStack PURIFIED_WATER = ItemCreator.create(Material.POTION, (item, meta) -> {
        PotionMeta potionMeta = (PotionMeta) meta;
        potionMeta.setColor(Color.fromRGB(110, 207, 255));
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("survive.is_purified"), PersistentDataType.BYTE, (byte)1);
        meta.setDisplayName("§eÁgua Purificada");
    });

}