package fr.firepro.factionbasics.commands;

import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class BottleXPCommand implements CommandExecutor, Listener {

    FactionBasics main;
    public BottleXPCommand(FactionBasics main) {this.main = main;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        int minimum_level = main.getConfig().getInt("bottlexp.minimum-level");
        int maximum_level = main.getConfig().getInt("bottlexp.maximum-level");
        if (player.getLevel() >= minimum_level) {
            if (strings.length == 0) {
                if (maximum_level >= player.getLevel()) {
                    int level = player.getLevel();
                    player.setLevel(0);

                    player.getInventory().addItem(createBottle(level));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.transform-message").replace("{level}", String.valueOf(level))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.maximum-level-message").replace("{maximum-level}", String.valueOf(main.getConfig().getInt("bottlexp.maximum-level")))));
                }
            } else{
                int level = 0;
                try {
                    level = Integer.parseInt(strings[0]);
                } catch (Exception e) {player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.number-error"))); return false;}
                if (player.getLevel() >= level) {
                    if (level <= main.getConfig().getInt("bottlexp.maximum-level")) {
                        player.setLevel(player.getLevel() - level);

                        player.getInventory().addItem(createBottle(level));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.transform-message").replace("{level}", String.valueOf(level))));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.maximum-level-message").replace("{maximum-level}", String.valueOf(main.getConfig().getInt("bottlexp.maximum-level")))));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.no-enough-xp")));
                }
                }
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.minimum-level-message").replace("{minimum-level}", String.valueOf(minimum_level))));

        return true;
    }
    @EventHandler
    public void RightClickEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();

        if ((a == Action.RIGHT_CLICK_AIR) || ((a == Action.RIGHT_CLICK_BLOCK))) {
            if ((event.getItem() != null) && (event.getItem().getType() == Material.EXP_BOTTLE) && (event.getItem().hasItemMeta())) {
                if ((event.getItem().getItemMeta().getDisplayName().contains("§6Bottle"))) {
                    event.setCancelled(true);
                    int bottlexp = Integer.parseInt(event.getItem().getItemMeta().getLore().get(2).replace("§e", ""));
                    p.setLevel(p.getLevel() + bottlexp);
                    if (event.getItem().getAmount() > 1) {
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else p.getInventory().removeItem(event.getItem());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bottlexp.receive-message").replace("{level}", String.valueOf(bottlexp))));
                }
            }
        }
    }
    private ItemStack createBottle(int level) {
        ItemStack itemStack = new ItemStack(Material.EXP_BOTTLE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName("§6Bottle : §e" + level + "");

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add("§6Bouteille d'xp contenant :");
        lore.add("§e" + level);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
