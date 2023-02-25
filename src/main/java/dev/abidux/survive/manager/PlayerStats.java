package dev.abidux.survive.manager;

import dev.abidux.survive.manager.skills.SkillSet;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerStats {

    public static final HashMap<String, PlayerStats> PLAYER_STATS = new HashMap<>();

    public static PlayerStats get(Player player) {
        PlayerStats stats = PLAYER_STATS.get(player.getName().toLowerCase());
        if (stats == null) PLAYER_STATS.put(player.getName().toLowerCase(), (stats = new PlayerStats()));
        return stats;
    }

    private SkillSet skillSet;
    public boolean showXp;

    public PlayerStats(SkillSet skillSet, boolean showXp) {
        this.skillSet = skillSet;
        this.showXp = showXp;
    }

    public PlayerStats() {
        this(new SkillSet(), true);
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }
}