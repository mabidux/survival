package dev.abidux.survive.manager;

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

    public PlayerStats(SkillSet skillSet) {
        this.skillSet = skillSet;
    }

    public PlayerStats() {
        this(new SkillSet());
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }
}