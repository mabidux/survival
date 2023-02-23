package dev.abidux.survive.event.mobs;

import dev.abidux.survive.util.LocationUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageByExplosionEvent implements Listener {

    @EventHandler
    void explode(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
        int zone = LocationUtil.getZone(event.getEntity().getLocation());
        double damage = event.getDamage() * (1 + zone * 15 / 100f);
        event.setDamage(damage);
    }

}