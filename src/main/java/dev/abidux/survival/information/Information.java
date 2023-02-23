package dev.abidux.survival.information;

import dev.abidux.survival.util.LocationUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Information {

    public static void sendActionbar(Player player) {
        sendActionbar(player, player.getLocation());
    }

    public static void sendActionbar(Player player, Location location) {
        int zone = LocationUtil.getZone(location);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§fVocê está entrando na " + getZoneName(zone)));
    }

    public static void updatePlayerList(Player player) {
        Location location = player.getLocation();
        BlockFace face = player.getFacing();
        int zone = LocationUtil.getZone(location);
        StringBuilder header = new StringBuilder();
        header.append("\n§fX: §e").append(location.getBlockX())
                .append("\n§fY: §e").append(location.getBlockY())
                .append("\n§fZ: §e").append(location.getBlockZ())
                .append("\n§f Olhando para §e").append(translate(face))
                .append(" (X: ").append(face.getModX()).append(", Z: ").append(face.getModZ())
                .append(") \n");

        StringBuilder footer = new StringBuilder();
        footer.append("\n§fVocê está na ").append(getZoneName(zone)).append("\n");
        player.setPlayerListHeaderFooter(header.toString(), footer.toString());
    }

    private static String translate(BlockFace face) {
        return switch(face) {
            case NORTH -> "Norte";
            case SOUTH -> "Sul";
            case EAST -> "Leste";
            case WEST -> "Oeste";
            default -> "?";
        };
    }

    public static String getZoneName(int zone) {
        return zone == 0 ? "§2Zona Segura" : "§eZona " + zone;
    }
}