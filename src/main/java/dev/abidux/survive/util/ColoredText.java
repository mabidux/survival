package dev.abidux.survive.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class ColoredText {

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