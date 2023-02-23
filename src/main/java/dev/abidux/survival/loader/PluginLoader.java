package dev.abidux.survival.loader;

import com.google.common.reflect.ClassPath;
import dev.abidux.survival.Main;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.scheduler.Scheduler;
import dev.abidux.survival.util.CommandRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.HashSet;

public class PluginLoader {

    private static final HashSet<Scheduler> SCHEDULERS = new HashSet<>();

    public static void loadPlugin() {
        Main.getInstance().saveDefaultConfig();
        try {
            registerClasses();
            loadSkills();
            Bukkit.getConsoleSender().sendMessage("§a[Survival] Plugin iniciado.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§c[Survival] Plugin não pôde ser iniciado.");
        }
    }

    public static void unloadPlugin() {
        for (Scheduler scheduler : SCHEDULERS) {
            scheduler.stop();
        }
    }

    private static void registerClasses() throws Exception {
        for (ClassPath.ClassInfo info : ClassPath.from(Main.class.getClassLoader()).getTopLevelClassesRecursive("dev.abidux.survival")) {
            Class<?> cls = Class.forName(info.getName());
            if (info.getName().endsWith(".Scheduler")) continue;

            if (Listener.class.isAssignableFrom(cls)) {
                Bukkit.getPluginManager().registerEvents((Listener) cls.newInstance(), Main.getInstance());
                Bukkit.getConsoleSender().sendMessage("§e[Survival] Evento '" + cls.getSimpleName() + "' registrado.");
            } else if (CommandExecutor.class.isAssignableFrom(cls) && cls.isAnnotationPresent(CommandRegistry.class)) {
                CommandRegistry registry = cls.getDeclaredAnnotation(CommandRegistry.class);
                Main.getInstance().getCommand(registry.value()).setExecutor((CommandExecutor) cls.newInstance());
                Bukkit.getConsoleSender().sendMessage("§e[Survival] Comando '" + cls.getSimpleName() + "' registrado.");
            } else if (Scheduler.class.isAssignableFrom(cls)) {
                Scheduler scheduler = (Scheduler) cls.newInstance();
                scheduler.start();
                SCHEDULERS.add(scheduler);
                Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), scheduler::start, 0, scheduler.ticks);
                Bukkit.getConsoleSender().sendMessage("§e[Survival] Scheduler '" + cls.getSimpleName() + "' registrado.");
            }
        }
    }

    private static void loadSkills() {
        if (!Main.getInstance().getConfig().isSet("skills")) return;
        Main.getInstance().getConfig().getConfigurationSection("skills").getKeys(true).forEach(key -> {
            String serializedSkills = Main.getInstance().getConfig().getString("skills." + key);
            SkillSet set = SkillSet.deserialize(serializedSkills);
            SkillManager.PLAYER_SKILLS.put(key, set);
        });
    }
}