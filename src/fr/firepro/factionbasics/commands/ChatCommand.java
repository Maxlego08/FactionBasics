package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class ChatCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        List<String> list = FactionBasics.getInstance().getConfig().getStringList("banned-command");

        for (String s : list) {
            if (event.getMessage().contains(s)){
                if (!event.getPlayer().isOp()){
                    event.setCancelled(true);
                }
            }
        }
    }

}
