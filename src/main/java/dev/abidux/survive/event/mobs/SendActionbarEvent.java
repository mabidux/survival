package dev.abidux.survive.event.mobs;

import dev.abidux.survive.information.Information;
import dev.abidux.survive.manager.ActionbarManager;
import dev.abidux.survive.util.LocationUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SendActionbarEvent implements Listener {

    @EventHandler
    void move(PlayerMoveEvent event) {
        int zoneFrom = LocationUtil.getZone(event.getFrom());
        int zoneTo = LocationUtil.getZone(event.getTo());
        if (zoneFrom != zoneTo) {
            Information.sendActionbar(event.getPlayer(), event.getTo());
        }
        ActionbarManager.PLAYER_LAST_ZONE_WARNING.put(event.getPlayer().getName(), System.currentTimeMillis());
    }

}