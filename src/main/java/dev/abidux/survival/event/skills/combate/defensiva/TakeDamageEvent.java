package dev.abidux.survival.event.skills.combate.defensiva;

import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.manager.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TakeDamageEvent implements Listener {

    @EventHandler
    void takeDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        SkillSet set = SkillManager.get(player);
        PlayerSkill skill = set.get(Skills.DEFENSIVA);

        double damage = Math.max(event.getDamage() - skill.getLevel(), 0);
        event.setDamage(damage);
        if (event.getDamager() instanceof Player) return;

        int receivedXp = (int) (damage - 4);
        if (receivedXp > 0 && event.getFinalDamage() < player.getHealth()) {
            skill.addXp(player, receivedXp);
        }
    }

}