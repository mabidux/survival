package dev.abidux.survive.event.mobs;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperBlockDestroyEvent implements Listener {

    @EventHandler
    void creeper(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER)
            event.setCancelled(true);
    }

}