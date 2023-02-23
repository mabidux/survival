package dev.abidux.survival.event.mobs;

import dev.abidux.survival.util.LocationUtil;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ProjectileHitEvent implements Listener {

    @EventHandler
    void hit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile projectile)) return;
        int zone = LocationUtil.getZone(projectile.getLocation());
        double damage = event.getDamage() * (1 + zone * 15 / 100f);
        event.setDamage(damage);
    }

}