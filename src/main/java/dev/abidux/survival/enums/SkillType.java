package dev.abidux.survival.enums;

import dev.abidux.survival.manager.PlayerSkill;
import dev.abidux.survival.manager.SkillSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum SkillType {
    FERRARIA(createComponent(new ColoredText("#f26418", "Fe"), new ColoredText("#f3712b", "rr"), new ColoredText("#f47e3e", "ar"), new ColoredText("#f5915b", "ia")),
            Material.IRON_INGOT, 50, 150, 250, 500, 750, 1000, 1250, 1500, -1),
    MINERAR(createComponent(new ColoredText("#5bd7f5", "Mi"), new ColoredText("#73ddf7", "ne"), new ColoredText("#87e1f8", "ra"), new ColoredText("#9ae6f9", "r")),
            Material.IRON_PICKAXE, 100, 500, 1000, 1500, 2000, 2500, 3500, 4500, 5000, -1),
    PLANTAR(createComponent(new ColoredText("#73350c", "Pl"), new ColoredText("#7d3a0d", "an"), new ColoredText("#69923a", "ta"), new ColoredText("#6a9e2e", "r")),
            Material.IRON_HOE, 100, 500, 1000, 1500, 2000, 2500, 3500, 4500, 5000, -1),
    SERRARIA(createComponent(new ColoredText("#c04821", "Se"), new ColoredText("#da5225", "rr"), new ColoredText("#de673f", "ar"), new ColoredText("#e37c59", "ia")),
            Material.IRON_AXE, 100, 500, 1000, 1500, 2000, 2500, -1),
    OFENSIVA(createComponent(new ColoredText("#ab0000", "Of"), new ColoredText("#d91a1a", "en"), new ColoredText("#c2280a", "si"), new ColoredText("#db3616", "va")),
            Material.IRON_SWORD),
    DEFENSIVA(createComponent(new ColoredText("#0b439e", "De"), new ColoredText("#0f52bd", "fe"), new ColoredText("#1d63d1", "ns"), new ColoredText("#3075e3", "iv"), new ColoredText("#397eed", "a")),
            Material.IRON_CHESTPLATE);

    private TextComponent name;
    private ItemStack icon;
    public final int[] levels;
    SkillType(TextComponent name, Material material, int... levels) {
        this.name = name;
        this.icon = new ItemStack(material);
        this.levels = levels;
    }

    public String getName() {
        return name.toLegacyText();
    }

    public TextComponent getComponent() {
        return name;
    }

    public ItemStack getIcon(SkillSet set) {
        PlayerSkill skill = set.get(this);
        ItemStack icon = this.icon.clone();
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name.toLegacyText());
        if (skill.level == levels.length - 1) {
            meta.setLore(Arrays.asList(
                    ChatColor.of("#fcba03") + "Nível: " + ChatColor.of("#a30808") + "M" + ChatColor.of("#bf0a0a") + "Á" + ChatColor.of("#eb1515") + "X"));
        } else {
            meta.setLore(Arrays.asList(
                    ChatColor.of("#fcba03") + "Nível: " + (skill.level + 1),
                    "§bXP: " + skill.xp + (levels.length > 0 ? "/" + levels[skill.level] : ""))
            );
        }
        icon.setItemMeta(meta);
        return icon;
    }

    record ColoredText(String color, String text) {}

    private static TextComponent createComponent(ColoredText... text) {
        TextComponent[] components = new TextComponent[text.length];
        for (int i = 0; i < text.length; i++) {
            TextComponent component = new TextComponent(text[i].text);
            component.setColor(ChatColor.of(text[i].color));
            components[i] = component;
        }
        return new TextComponent(components);
    }
}