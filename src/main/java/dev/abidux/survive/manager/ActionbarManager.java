package dev.abidux.survive.manager;

import dev.abidux.survive.manager.food.ThirstSystem;
import dev.abidux.survive.manager.skills.SkillXpAnnouncement;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ActionbarManager {

    public static final HashMap<String, Long> PLAYER_LAST_ZONE_WARNING = new HashMap<>();

    public static void update(Player player) {
        update(player, false);
    }

    public static void update(Player player, boolean force) {
        if (!force) {
            Long last = PLAYER_LAST_ZONE_WARNING.get(player.getName());
            if (last != null && last + 1500 >= System.currentTimeMillis()) return;
        }
        PlayerStats stats = PlayerStats.get(player);
        ThirstSystem thirstSystem = stats.getThirstSystem();

        String sedeMessage = "§bSede: " + thirstSystem.getThirst();
        SkillXpAnnouncement announce = stats.skillXpAnnouncement;
        boolean shouldShowXp = announce.announceTime + 2000 >= System.currentTimeMillis();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, shouldShowXp
                ? new TextComponent(new TextComponent(sedeMessage + " §7| §b" + announce.xp + "/" + announce.max + " §7- "),
                announce.skill.name.component)
                : new TextComponent(sedeMessage));
    }

}