package dev.abidux.survive.scheduler.food;

import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.food.ThirstSystem;
import dev.abidux.survive.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevelCheckerScheduler extends Scheduler {
    public LevelCheckerScheduler() {
        super(100);
    }

    @Override
    public void start() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerStats stats = PlayerStats.get(player);
            ThirstSystem thirstSystem = stats.getThirstSystem();
            if (player.getFoodLevel() <= 6 || thirstSystem.getThirst() <= 6) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
            }
            if (thirstSystem.getThirst() == 0) {
                player.damage(2);
            }
        });
    }

    @Override
    public void stop() {}
}