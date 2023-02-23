package dev.abidux.survival.scheduler;

import dev.abidux.survival.Main;
import dev.abidux.survival.manager.SkillManager;

public class SaveScheduler extends Scheduler {

    public SaveScheduler() {
        super(20 * 60 * 15);
    }

    @Override
    public void start() {
        saveSkills();
        Main.getInstance().saveConfig();
    }

    @Override
    public void stop() {
        start();
    }

    private void saveSkills() {
        SkillManager.PLAYER_SKILLS.forEach((player, skillSet) -> Main.getInstance().getConfig().set("skills." + player, skillSet.toString()));
    }
}