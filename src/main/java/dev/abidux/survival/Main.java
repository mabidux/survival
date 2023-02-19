package dev.abidux.survival;

import dev.abidux.survival.commands.SkillsCommand;
import dev.abidux.survival.event.skills.SkillInventoryClick;
import dev.abidux.survival.event.skills.ferraria.CraftEvent;
import dev.abidux.survival.event.betterdoors.OpenDoorEvent;
import dev.abidux.survival.event.skills.minerar.MineBlockEvent;
import dev.abidux.survival.event.skills.plantar.CreateFarmlandEvent;
import dev.abidux.survival.event.skills.plantar.HarvestEvent;
import dev.abidux.survival.event.skills.serraria.BreakLogEvent;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Main extends JavaPlugin {

    public static final Random random = new Random();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSkills();

        getCommand("skills").setExecutor(new SkillsCommand());

        Bukkit.getPluginManager().registerEvents(new OpenDoorEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CraftEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MineBlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SkillInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new BreakLogEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HarvestEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CreateFarmlandEvent(), this);

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
