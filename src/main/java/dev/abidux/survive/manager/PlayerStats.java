package dev.abidux.survive.manager;

import dev.abidux.survive.manager.food.FoodSystem;
import dev.abidux.survive.manager.food.ThirstSystem;
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
    private FoodSystem foodSystem;
    private ThirstSystem thirstSystem;
    public boolean showXp;

    public PlayerStats(SkillSet skillSet, FoodSystem foodSystem, ThirstSystem thirstSystem, boolean showXp) {
        this.skillSet = skillSet;
        this.foodSystem = foodSystem;
        this.thirstSystem = thirstSystem;
        this.showXp = showXp;
    }

    public PlayerStats() {
        this(new SkillSet(), new FoodSystem(), new ThirstSystem(20), true);
    }

    public FoodSystem getFoodSystem() {
        return foodSystem;
    }

    public ThirstSystem getThirstSystem() {
        return thirstSystem;
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }
}