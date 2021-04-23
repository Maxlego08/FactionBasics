package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SiteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        TextComponent chat = new TextComponent("");
        chat.setText(ChatColor.translateAlternateColorCodes('&', FactionBasics.getInstance().getConfig().getString("command.site")));
        Player player = (Player) commandSender;
        player.spigot().sendMessage(chat);
        return false;
    }
}
