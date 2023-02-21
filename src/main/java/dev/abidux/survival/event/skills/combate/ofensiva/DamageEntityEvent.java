package dev.abidux.survival.event.skills.combate.ofensiva;

import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.manager.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityEvent implements Listener {

    @EventHandler
    void damage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        SkillSet set = SkillManager.get(player);
        PlayerSkill skill = set.get(Skills.OFENSIVA);

        double modifiedDamage = event.getDamage() + skill.getLevel();
        double damage = modifiedDamage * player.getAttackCooldown();
        event.setDamage((int) damage);
    }

}