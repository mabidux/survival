package dev.abidux.survival.commands;

import dev.abidux.survival.manager.SkillManager;
import dev.abidux.survival.manager.SkillSet;
import dev.abidux.survival.manager.Skills;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SkillsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Inventory inventory = args.length == 0 ? createForPlayer(player) : createForThird(args[0]);
        player.openInventory(inventory);
        return false;
    }

    private Inventory createForPlayer(Player player) {
        SkillSet set = SkillManager.get(player);
        return buildInventory(set);
    }

    private Inventory createForThird(String player) {
        SkillSet set = SkillManager.PLAYER_SKILLS.getOrDefault(player.toLowerCase(), new SkillSet());
        return buildInventory(set);
    }

    private Inventory buildInventory(SkillSet set) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Skills");
        inventory.setItem(10, Skills.FERRARIA.buildIcon(set));
        inventory.setItem(11, Skills.MINERAR.buildIcon(set));
        inventory.setItem(12, Skills.PLANTAR.buildIcon(set));
        inventory.setItem(13, Skills.SERRARIA.buildIcon(set));

        inventory.setItem(15, Skills.OFENSIVA.buildIcon(set));
        inventory.setItem(16, Skills.DEFENSIVA.buildIcon(set));
        return inventory;
    }
}