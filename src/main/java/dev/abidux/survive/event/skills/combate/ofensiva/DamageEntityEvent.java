package dev.abidux.survive.event.skills.combate.ofensiva;

import dev.abidux.survive.manager.PlayerSkill;
import dev.abidux.survive.manager.PlayerStats;
import dev.abidux.survive.manager.SkillSet;
import dev.abidux.survive.manager.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityEvent implements Listener {

    @EventHandler
    void damage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        PlayerStats stats = PlayerStats.get(player);
        PlayerSkill skill = stats.getSkillSet().get(Skills.OFENSIVA);

        double modifiedDamage = event.getDamage() + skill.getLevel();
        double damage = modifiedDamage * player.getAttackCooldown();
        event.setDamage((int) damage);
    }

}