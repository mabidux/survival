package dev.abidux.survival.manager;

import dev.abidux.survival.model.skills.skill.CappedSkill;
import dev.abidux.survival.model.skills.skill.Skill;
import dev.abidux.survival.model.skills.skill.UncappedSkill;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerSkill {

    public final Skill type;
    private int level, xp;
    public PlayerSkill(Skill type, int level, int xp) {
        this.type = type;
        this.level = level;
        this.xp = xp;
    }

    public void addXp(Player player, int amount) {
        addXp(player, amount, false);
    }

    public void addXp(Player player, int amount, boolean silent) {
        int evolve = type instanceof CappedSkill cap ? cap.levels[level] : ((UncappedSkill) type).calculateXpByLevelFunction.apply(level);
        if (evolve == -1) return;
        xp += amount;

        if (silent) return;
        TextComponent component;
        if (xp >= evolve) {
            xp = 0;
            level++;
            player.sendMessage("§6+1 LEVEL §7- " + type.name.legacyText);
            component = new TextComponent(new TextComponent("§6+1 LEVEL §7- "), type.name.component);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else component = new TextComponent(new TextComponent("§b" + xp + "/" + evolve + " XP §7- "), type.name.component);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    public boolean isMaxed() {
        return type instanceof CappedSkill capped && capped.levels[level] == -1;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }
}