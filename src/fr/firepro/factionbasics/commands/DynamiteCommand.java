package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import fr.firepro.factionbasics.utils.dynamite.DynamiteItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DynamiteCommand implements CommandExecutor {
    FactionBasics main;
    public DynamiteCommand(FactionBasics main){
        this.main = main;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dynamite")) {
            if (args.length == 0) {
                sender.sendMessage("§7/dynamite <pseudo> <nombre>");
                return true;
            }
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) != null && isInt(args[1])) {
                    Player target = Bukkit.getPlayer(args[0]);
                    ItemStack item = (new DynamiteItem(main)).item();
                    item.setAmount(Integer.parseInt(args[1]));
                    target.getInventory().addItem(new ItemStack[] { item });

                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("dynamite.receveur").replace("{nombre}", args[1])));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("dynamite.giveur").replace("{player}", args[0]).replace("{nombre}", args[1])));
                    return true;
                }
                sender.sendMessage("§7/dynamite <pseudo> <nombre>");
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}