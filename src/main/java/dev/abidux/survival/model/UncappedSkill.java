package dev.abidux.survival.model;

import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillSet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.IntFunction;

public class UncappedSkill extends Skill {

    public final IntFunction<Integer> calculateXpByLevelFunction;
    public UncappedSkill(ColoredText name, Material material, IntFunction<Integer> calculateXpByLevelFunction) {
        super(name, material);
        this.calculateXpByLevelFunction = calculateXpByLevelFunction;
    }

    @Override
    public ItemStack buildIcon(SkillSet set) {
        PlayerSkill skill = set.get(this);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name.legacyText);
        meta.setLore(Arrays.asList(
                ChatColor.of("#fcba03") + "Nível: " + (skill.level + 1),
                "§bXP: " + skill.xp + "/" + calculateXpByLevelFunction.apply(skill.level))
        );
        item.setItemMeta(meta);
        return item;
    }
}