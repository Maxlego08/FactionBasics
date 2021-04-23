package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MaintenanceCommand implements CommandExecutor {

    FactionBasics main;
    public MaintenanceCommand(FactionBasics main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (this.main.getConfig().getBoolean("maintenance.on")) {
            this.main.getConfig().set("maintenance.on", false);
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("maintenance.disable-message")));
            this.main.saveConfig();
        } else {
            this.main.getConfig().set("maintenance.on", true);
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("maintenance.enable-message")));
            this.main.saveConfig();
        }
        return true;
    }
}