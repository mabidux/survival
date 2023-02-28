package dev.abidux.survive;

import dev.abidux.survive.loader.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static final Random random = new Random();

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("main");
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
