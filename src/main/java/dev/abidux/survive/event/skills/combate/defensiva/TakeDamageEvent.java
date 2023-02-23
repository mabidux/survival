package dev.abidux.survive.event.skills.combate.defensiva;

import dev.abidux.survive.manager.PlayerSkill;
import dev.abidux.survive.manager.SkillManager;
import dev.abidux.survive.manager.SkillSet;
import dev.abidux.survive.manager.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class TakeDamageEvent implements Listener {

    @EventHandler
    void takeDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        SkillSet set = SkillManager.get(player);
        PlayerSkill skill = set.get(Skills.DEFENSIVA);
        double damage = Math.max(event.getDamage() - skill.getLevel(), 0);
        event.setDamage(damage);
    }

    @EventHandler
    void takeDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player) || event.getDamager() instanceof Player) return;
        SkillSet set = SkillManager.get(player);
        PlayerSkill skill = set.get(Skills.DEFENSIVA);
        double damage = Math.max(event.getDamage() - skill.getLevel(), 0);
        int receivedXp = (int) (damage - 4);
        if (receivedXp > 0 && event.getFinalDamage() < player.getHealth()) {
            skill.addXp(player, receivedXp);
        }
    }

}