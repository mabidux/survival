package dev.abidux.survive.manager;

import dev.abidux.survive.model.skills.skill.Skill;

import java.util.HashMap;
import java.util.stream.Collectors;

public class SkillSet extends HashMap<Skill, PlayerSkill> {

    @Override
    public PlayerSkill get(Object key) {
        if (!(key instanceof Skill type)) return null;
        PlayerSkill skill = super.get(type);
        if (skill == null) put(type, (skill = new PlayerSkill(type, 0, 0)));
        return skill;
    }

    @Override
    public String toString() {
        return entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue().getLevel() + "/" + entry.getValue().getXp()).collect(Collectors.joining(";"));
    }

    public static SkillSet deserialize(String serialized) {
        SkillSet set = new SkillSet();
        for (String part : serialized.split(";")) {
            String[] s = part.split(":");
            String[] l = s[1].split("/");
            Skill type = Skills.getSkillByName(s[0]);
            int level = Integer.parseInt(l[0]);
            int xp = Integer.parseInt(l[1]);
            set.put(type, new PlayerSkill(type, level, xp));
        }
        return set;
    }
}