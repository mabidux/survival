package dev.abidux.survive.util.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {

    public static ItemStack create(Material material, ItemData data) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        data.get(item, meta);
        item.setItemMeta(meta);
        return item;
    }

    public interface ItemData {
        void get(ItemStack item, ItemMeta meta);
    }

}