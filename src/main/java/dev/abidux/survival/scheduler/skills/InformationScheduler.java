package dev.abidux.survival.scheduler.skills;

import dev.abidux.survival.information.Information;
import dev.abidux.survival.scheduler.Scheduler;
import dev.abidux.survival.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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