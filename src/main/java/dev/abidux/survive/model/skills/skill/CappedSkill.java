package dev.abidux.survive.model.skills.skill;

import dev.abidux.survive.manager.PlayerSkill;
import dev.abidux.survive.manager.SkillSet;
import dev.abidux.survive.util.ColoredText;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CappedSkill extends Skill {

    public final int[] levels;
    public CappedSkill(ColoredText name, Material material, int... levels) {
        super(name, material);
        this.levels = levels;
    }

    @Override
    public ItemStack buildIcon(SkillSet set) {
        PlayerSkill skill = set.get(this);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name.legacyText);
        int level = skill.getLevel();
        int xp = skill.getXp();
        if (level == levels.length - 1) {
            meta.setLore(Arrays.asList(
                    ChatColor.of("#fcba03") + "Nível: " + ChatColor.of("#a30808") + "M" + ChatColor.of("#bf0a0a") + "Á" + ChatColor.of("#eb1515") + "X"));
        } else {
            meta.setLore(Arrays.asList(
                    ChatColor.of("#fcba03") + "Nível: " + (level + 1),
                    "§bXP: " + xp + "/" + levels[level])
            );
        }
        item.setItemMeta(meta);
        return item;
    }
}