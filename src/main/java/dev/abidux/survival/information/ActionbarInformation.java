package dev.abidux.survival.information;

import dev.abidux.survival.util.LocationUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ActionbarInformation {

    public static void send(Player player) {
        send(player, player.getLocation());
    }

    public static void send(Player player, Location location) {
        int zone = LocationUtil.getZone(location);
        String zoneName = zone == 0 ? "§2Zona Segura" : "§eZona " + zone;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(zoneName));
    }

}