package dev.abidux.survival.util;

import dev.abidux.survival.config.Configuration;
import org.bukkit.Location;

public class LocationUtil {

    public static double plainDistance(Location a, Location b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public static double distanceFromSpawn(Location location) {
        return Math.sqrt(Math.pow(location.getX(), 2) + Math.pow(location.getZ(), 2));
    }

    public static int getZone(Location location) {
        double distance = distanceFromSpawn(location);
        int zone = (int)distance / Configuration.ZONE_SIZE;
        return location.getWorld().getName().equals("world") ? zone : zone + 1;
    }

}