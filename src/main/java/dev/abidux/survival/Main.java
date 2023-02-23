package dev.abidux.survival;

import com.google.common.reflect.ClassPath;
import dev.abidux.survival.loader.PluginLoader;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.util.CommandRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
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
