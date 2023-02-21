package dev.abidux.survival;

import com.google.common.reflect.ClassPath;
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

    public static final Random random = new Random();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSkills();

        loadPlugin();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            saveSkills();
            saveConfig();
        }, 0, 20 * 60 * 10);
    }

    @Override
    public void onDisable() {
        saveSkills();
        saveConfig();
    }

    private void loadPlugin() {
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(getClassLoader()).getTopLevelClassesRecursive("dev.abidux.survival")) {
                Class<?> cls = Class.forName(info.getName());
                if (Listener.class.isAssignableFrom(cls)) {
                    Bukkit.getPluginManager().registerEvents((Listener) cls.newInstance(), this);
                    Bukkit.getConsoleSender().sendMessage("§e[Survival] Evento '" + cls.getSimpleName() + "' registrado.");
                } else if (CommandExecutor.class.isAssignableFrom(cls) && cls.isAnnotationPresent(CommandRegistry.class)) {
                    CommandRegistry registry = cls.getDeclaredAnnotation(CommandRegistry.class);
                    getCommand(registry.value()).setExecutor((CommandExecutor) cls.newInstance());
                    Bukkit.getConsoleSender().sendMessage("§e[Survival] Comando '" + cls.getSimpleName() + "' registrado.");
                }
            }
            Bukkit.getConsoleSender().sendMessage("§a[Survival] Plugin iniciado.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§c[Survival] Plugin não pôde ser iniciado.");
        }
    }

    public void saveSkills() {
        SkillManager.PLAYER_SKILLS.forEach((player, skillSet) -> getConfig().set("skills." + player, skillSet.toString()));
    }

    public void loadSkills() {
        if (!getConfig().isSet("skills")) return;
        getConfig().getConfigurationSection("skills").getKeys(true).forEach(key -> {
            String serializedSkills = getConfig().getString("skills." + key);
            SkillSet set = SkillSet.deserialize(serializedSkills);
            SkillManager.PLAYER_SKILLS.put(key, set);
        });
    }
}
