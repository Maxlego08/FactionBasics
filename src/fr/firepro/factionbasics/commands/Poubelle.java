package fr.firepro.factionbasics.commands;


import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class Poubelle implements Listener, CommandExecutor {


    private Inventory inv;



    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
                if (args.length == 0) {
                    Trash(p);
                } else {
                    p.sendMessage(FactionBasics.getInstance().getConfig().getString("poubelle.messages.error").replaceAll("&", "§"));
                }
        } else {
            sender.sendMessage(FactionBasics.getInstance().getConfig().getString("poubelle.messages.error_only_player").replaceAll("&", "§"));
        }
        return true;
    }

    public void Trash(Player p) {
        this.inv = Bukkit.createInventory(null, 54, FactionBasics.getInstance().getConfig().getString("poubelle.nom.trashname").replaceAll("&", "§"));
        p.openInventory(this.inv);
    }
}
