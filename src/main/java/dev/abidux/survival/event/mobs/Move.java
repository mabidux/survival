package dev.abidux.survival.event.mobs;

import dev.abidux.survival.information.ActionbarInformation;
import dev.abidux.survival.util.LocationUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Move implements Listener {

    @EventHandler
    void move(PlayerMoveEvent event) {
        int zoneFrom = LocationUtil.getZone(event.getFrom());
        int zoneTo = LocationUtil.getZone(event.getTo());
        if (zoneFrom != zoneTo) {
            ActionbarInformation.send(event.getPlayer(), event.getTo());
        }
    }

}