package dev.abidux.survival.scheduler.skills;

import dev.abidux.survival.information.ActionbarInformation;
import dev.abidux.survival.scheduler.Scheduler;
import org.bukkit.Bukkit;

public class InformationScheduler extends Scheduler {

    public InformationScheduler() {
        super(10);
    }

    @Override
    public void start() {
        Bukkit.getOnlinePlayers().forEach(ActionbarInformation::send);
    }

    @Override
    public void stop() {}
}