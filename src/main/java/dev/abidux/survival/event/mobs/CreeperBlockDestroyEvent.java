package dev.abidux.survival.event.mobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class CreeperBlockDestroyEvent implements Listener {

    @EventHandler
    void creeper(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

}