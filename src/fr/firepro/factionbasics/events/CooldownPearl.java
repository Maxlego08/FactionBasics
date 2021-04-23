package fr.firepro.factionbasics.events;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class CooldownPearl implements Listener {
    List<String> regionList = FactionBasics.getInstance().getConfig().getStringList("cooldownPearl.region");
    List<Long> timeList = FactionBasics.getInstance().getConfig().getLongList("cooldownPearl.time");
    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onPearl(PlayerInteractEvent event) {
        FLocation fLocation = new FLocation(event.getPlayer().getLocation().getWorld().getName(), event.getPlayer().getLocation().getChunk().getX(), event.getPlayer().getLocation().getChunk().getZ());
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        try {
            if (!event.getPlayer().hasPermission("cooldown.bypass") && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) && event.getItem().getType().equals(Material.ENDER_PEARL)) {
                String region = enterRegion(event.getPlayer());
                if ((Long) this.timeList.get(this.regionList.indexOf(region)) == -1L) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(FactionBasics.getInstance().getConfig().getString("cooldownPearl.impossible").replace("&", "§").replace("%region%", region));
                    event.getPlayer().updateInventory();
                } else if (faction.getId() == null || !region.equalsIgnoreCase("nature")) {
                    if (this.cooldown.containsKey(event.getPlayer().getUniqueId())) {
                        if ((Long) this.cooldown.get(event.getPlayer().getUniqueId()) > System.currentTimeMillis()) {
                            event.setCancelled(true);
                            Long timeSecond = ((Long) this.cooldown.get(event.getPlayer().getUniqueId()) - System.currentTimeMillis()) / 1000L;
                            event.getPlayer().sendMessage(FactionBasics.getInstance().getConfig().getString("cooldownPearl.message").replace("&", "§").replace("%cooldown%", timeSecond.toString()));
                            event.getPlayer().updateInventory();
                        } else if (this.regionList.contains(region)) {
                            this.cooldown.replace(event.getPlayer().getUniqueId(), System.currentTimeMillis() + (Long) this.timeList.get(this.regionList.indexOf(region)) * 1000L);
                        }
                    } else if (this.regionList.contains(region)) {
                        this.cooldown.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + (Long) this.timeList.get(this.regionList.indexOf(region)) * 1000L);
                    }
                }
            }
        } catch (Exception exception) {
        }
    }


    public String enterRegion(Player player) {
        LocalPlayer localPlayer = getWorldGuard().wrapPlayer(player);
        Vector playerVector = localPlayer.getPosition();
        RegionManager regionManager = getWorldGuard().getRegionManager(player.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);

        for (ProtectedRegion regions : applicableRegionSet) {
            if (regions.contains(playerVector)) {
                return regions.getId();
            }

        }
        return "nature";
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin =

                FactionBasics.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
}
