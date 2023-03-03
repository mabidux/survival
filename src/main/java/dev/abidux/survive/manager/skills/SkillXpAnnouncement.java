package dev.abidux.survive.manager.skills;

import dev.abidux.survive.model.skills.skill.Skill;

public class SkillXpAnnouncement {

    public final Skill skill;
    public final long xp, max, announceTime;
    public SkillXpAnnouncement(Skill skill, long xp, long max) {
        this(skill,xp, max, System.currentTimeMillis());
    }

    public SkillXpAnnouncement(Skill skill, long xp, long max, long announceTime) {
        this.skill = skill;
        this.xp = xp;
        this.max = max;
        this.announceTime = announceTime;
    }

}