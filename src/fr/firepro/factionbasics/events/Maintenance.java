package fr.firepro.factionbasics.events;

import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Maintenance implements Listener {

    FactionBasics main;
    public Maintenance(FactionBasics main) {
        this.main = main;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (main.getConfig().getBoolean("maintenance.on")) {
            if (player.hasPermission("factionbasics.maintenance.bypass")) {
                event.setJoinMessage(event.getJoinMessage() + " §4§l(Maintenance)");
            } else {
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("maintenance.kick-message")));
            }
        }
    }
}
