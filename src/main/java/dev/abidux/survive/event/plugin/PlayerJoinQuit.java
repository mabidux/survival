package dev.abidux.survive.event.plugin;

import dev.abidux.survive.manager.ActionbarManager;
import dev.abidux.survive.util.chat.ColoredText;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuit implements Listener {

    @EventHandler
    void join(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        String message = "§8[§a+§8] §a" + event.getPlayer().getName();
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        event.getPlayer().sendMessage(new ColoredText("{#e07726}Bem-vindo a {#c72412}Survive: Food Update{#e07726}!").legacyText);
    }

    @EventHandler
    void quit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        String message = "§8[§c-§8] §c" + event.getPlayer().getName();
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));

        ActionbarManager.PLAYER_LAST_ZONE_WARNING.remove(event.getPlayer().getName());
    }
}