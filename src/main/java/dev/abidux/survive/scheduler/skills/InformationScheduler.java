package dev.abidux.survive.scheduler.skills;

import dev.abidux.survive.information.Information;
import dev.abidux.survive.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InformationScheduler extends Scheduler {

    public InformationScheduler() {
        super(10);
    }

    @Override
    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Information.updatePlayerList(player);
        }
    }

    @Override
    public void stop() {}
}