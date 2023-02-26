package dev.abidux.survive.scheduler.food;

import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.scheduler.Scheduler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ExpireSicknessScheduler extends Scheduler {
    public ExpireSicknessScheduler() {
        super(100);
    }

    @Override
    public void start() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerStats stats = PlayerStats.get(player);
            ArrayList<Material> removed = stats.getFoodSystem().executeExpirationProcess();
            removed.forEach(material -> sendExpiredSickness(player, material));
        });
    }

    @Override
    public void stop() {
        start();
    }

    private static final ChatColor YELLOWISH_ORANGE = ChatColor.of("#eb9e34");
    private static final ChatColor ORANGE = ChatColor.of("#eb8334");
    private void sendExpiredSickness(Player player, Material material) {
        player.sendMessage(YELLOWISH_ORANGE + "Talvez comer " + ORANGE + prettifyMaterial(material) + YELLOWISH_ORANGE + " não pareça tão ruim...");
    }

    private String prettifyMaterial(Material material) {
        String name = material.toString().toLowerCase();
        String[] s = name.split("_");
        return Arrays.stream(s).map(l -> Character.toUpperCase(l.charAt(0)) + l.substring(1)).collect(Collectors.joining(" "));
    }
}