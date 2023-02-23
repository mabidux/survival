package dev.abidux.survive.event.mobs;

import dev.abidux.survive.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.function.Function;

public class MobSpawnEvent implements Listener {

    @EventHandler
    void spawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        Location location = event.getLocation();
        int zone = LocationUtil.getZone(location);
        if (zone == 0 && shouldCancelSpawn(entity.getType())) {
            event.setCancelled(true);
            return;
        }
        float modifier = 1 + zone * 15 / 100f;

        changeAttribute(entity, Attribute.GENERIC_MAX_HEALTH, base -> base * modifier);
        changeAttribute(entity, Attribute.GENERIC_ATTACK_DAMAGE, base -> base * modifier);
        changeAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED, base -> Math.min(base * modifier, .75));
        changeAttribute(entity, Attribute.GENERIC_FOLLOW_RANGE, base -> Math.min(base * modifier, 64));
    }

    private static void changeAttribute(LivingEntity entity, Attribute attribute, Function<Double, Double> baseToModified) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(baseToModified.apply(instance.getBaseValue()));
        }
    }

    private static boolean shouldCancelSpawn(EntityType type) {
        return switch(type) {
            case ZOMBIE, HUSK, ENDERMAN, CREEPER, EVOKER, CAVE_SPIDER, DROWNED, ENDERMITE, ZOMBIE_VILLAGER, WITCH, VINDICATOR, VEX, STRAY, SPIDER, SLIME, SKELETON, SILVERFISH, PHANTOM, RAVAGER, PILLAGER -> true;
            default -> false;
        };
    }

}