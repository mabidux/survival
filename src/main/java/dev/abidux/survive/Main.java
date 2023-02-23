package dev.abidux.survive;

import dev.abidux.survive.loader.PluginLoader;
import dev.abidux.survive.util.ColoredText;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Main extends JavaPlugin {

    // TODO
    // fazer com que o xp seja long, em vez de int

    private static Main instance;
    public static final Random random = new Random();

    @Override
    public void onEnable() {
        instance = this;
        PluginLoader.loadPlugin();
    }

    @Override
    public void onDisable() {
        PluginLoader.unloadPlugin();
    }

    public static Main getInstance() {
        return instance;
    }
}
