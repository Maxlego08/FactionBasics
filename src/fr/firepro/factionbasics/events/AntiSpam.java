package fr.firepro.factionbasics.events;

import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AntiSpam implements Listener {
    private HashMap<UUID, Long> cooldown = new HashMap<>();


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        Player p = e.getPlayer();
        if (!p.hasPermission("factionbasics.antispam.bypass"))
            if (this.cooldown.containsKey(uuid)) {
                float time = (float) ((System.currentTimeMillis() - ((Long) this.cooldown.get(uuid)).longValue()) / 1000L);
                if (time < 2.0F) {
                    String noSpam = FactionBasics.getInstance().getConfigMessage().getString("SPAM_MESSAGE").replace("&", "§");
                    e.setCancelled(true);
                    p.sendMessage(noSpam);
                } else {
                    this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
                }
            } else {
                this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
            }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        this.cooldown.remove(e.getPlayer().getUniqueId());
    }

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
