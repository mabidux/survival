package dev.abidux.survive.scheduler.food;

import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.food.FoodSystem;
import dev.abidux.survive.scheduler.Scheduler;

public class ExpireSicknessScheduler extends Scheduler {
    public ExpireSicknessScheduler() {
        super(100);
    }

    @Override
    public void start() {
        PlayerStats.PLAYER_STATS.forEach(($0, stats) -> {
            stats.getFoodSystem().executeExpirationProcess();
        });
    }

    @Override
    public void stop() {
        start();
    }
}