package dev.abidux.survive.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkillManager {

    public static final HashMap<String, SkillSet> PLAYER_SKILLS = new HashMap<>();
    
    public static SkillSet get(Player player) {
        SkillSet set = PLAYER_SKILLS.get(player.getName().toLowerCase());
        if (set == null) PLAYER_SKILLS.put(player.getName().toLowerCase(), (set = new SkillSet()));
        return set;
    }

}