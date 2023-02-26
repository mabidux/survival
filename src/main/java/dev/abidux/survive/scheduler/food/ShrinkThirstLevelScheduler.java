package dev.abidux.survive.scheduler.food;

import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.food.ThirstSystem;
import dev.abidux.survive.scheduler.Scheduler;
import org.bukkit.Bukkit;

public class ShrinkThirstLevelScheduler extends Scheduler {
    public ShrinkThirstLevelScheduler() {
        super(20 * 60);
    }

    @Override
    public void start() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerStats stats = PlayerStats.get(player);
            ThirstSystem thirstSystem = stats.getThirstSystem();
            thirstSystem.setThirst(thirstSystem.getThirst() - 1);
        });
    }

    @Override
    public void stop() {}
}