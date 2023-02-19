package dev.abidux.survival.model;

import dev.abidux.survival.manager.SkillSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Skill {

    public final ColoredText name;
    public final Material material;
    public Skill(ColoredText name, Material material) {
        this.material = material;
        this.name = name;
    }

    public abstract ItemStack buildIcon(SkillSet set);

    public static class ColoredText {

        public final String legacyText;
        public final TextComponent component;
        public ColoredText(String text) {
            boolean startsColored = text.charAt(0) == '#';
            String[] parts = text.split("\\{#");
            TextComponent[] components = new TextComponent[parts.length];
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (i == 0 && !startsColored) {
                    components[i] = new TextComponent(part);
                    continue;
                }
                String colorCode = "#" + part.substring(0, 6);
                String partText = part.substring(7);
                TextComponent component = new TextComponent(partText);
                component.setColor(ChatColor.of(colorCode));
                components[i] = component;
            }

            TextComponent component = new TextComponent(components);
            this.component = component;
            this.legacyText = component.toLegacyText();
        }

    }

    @Override
    public String toString() {
        return name.component.toPlainText().toUpperCase();
    }
}