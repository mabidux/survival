package dev.abidux.survive.event.skills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SkillInventoryClick implements Listener {

    @EventHandler
    void click(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Skills")) event.setCancelled(true);
    }
}