package dev.abidux.survival.event.mobs;

import dev.abidux.survival.util.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class DamageByBlockEvent implements Listener {

    @EventHandler
    void damage(EntityDamageByBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        int zone = LocationUtil.getZone(player.getLocation());
        double modifier = 1 + zone / 4f;
        event.setDamage(event.getDamage() * modifier);
        System.out.println(event.getDamage());
    }

}