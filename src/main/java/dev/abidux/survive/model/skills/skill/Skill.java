package dev.abidux.survive.model.skills.skill;

import dev.abidux.survive.manager.skills.SkillSet;
import dev.abidux.survive.util.chat.ColoredText;
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

    @Override
    public String toString() {
        return name.component.toPlainText().toUpperCase();
    }
}