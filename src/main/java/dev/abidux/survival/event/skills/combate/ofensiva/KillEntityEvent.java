package dev.abidux.survival.event.skills.combate.ofensiva;

import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.manager.Skills;
import dev.abidux.survival.util.LocationUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntityEvent implements Listener {

    @EventHandler
    void kill(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player || entity.getKiller() == null) return;

        int xp = getEntityXp(entity.getType());
        if (xp == 0) return;
        int zone = LocationUtil.getZone(entity.getLocation());
        xp *= 1 + zone / 4f;

        Player player = entity.getKiller();
        SkillSet set = SkillManager.get(player);
        PlayerSkill skill = set.get(Skills.OFENSIVA);

        skill.addXp(player, xp);
    }

    private int getEntityXp(EntityType type) {
        return switch(type) {
            case ZOMBIE, SKELETON, SPIDER, CAVE_SPIDER, CREEPER, PIGLIN, ZOMBIFIED_PIGLIN, DROWNED, ZOMBIE_VILLAGER, HUSK, STRAY, MAGMA_CUBE, ENDERMITE -> 1;
            case ENDERMAN, PIGLIN_BRUTE, ZOGLIN, BLAZE, GHAST, IRON_GOLEM, WITCH, HOGLIN, SHULKER, PILLAGER -> 2;
            case EVOKER, ILLUSIONER, VINDICATOR, WITHER_SKELETON, PHANTOM, GUARDIAN, VEX -> 5;
            case RAVAGER -> 10;
            case ELDER_GUARDIAN -> 250;
            case WITHER -> 500;
            case WARDEN, ENDER_DRAGON -> 2000;
            default -> 0;
        };
    }
}