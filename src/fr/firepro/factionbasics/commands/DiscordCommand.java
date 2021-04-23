package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', FactionBasics.getInstance().getConfig().getString("command.discord")));
        return false;
    }
}
