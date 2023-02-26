package dev.abidux.survive.manager;

import dev.abidux.survive.manager.food.ThirstSystem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ActionbarManager {

    public static final HashMap<String, Long> PLAYER_LAST_ZONE_WARNING = new HashMap<>();

    public static void update(Player player) {
        update(player, false);
    }

    public static void update(Player player, boolean force) {
        if (!force) {
            Long last = PLAYER_LAST_ZONE_WARNING.get(player.getName());
            if (last != null && last + 1500 >= System.currentTimeMillis()) return;
        }
        PlayerStats stats = PlayerStats.get(player);
        ThirstSystem thirstSystem = stats.getThirstSystem();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bSede: " + thirstSystem.getThirst()));
    }

}