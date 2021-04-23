package fr.firepro.factionbasics.utils;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class AntiWorldDownloaderUtils implements Listener, PluginMessageListener {
    public FactionBasics plugin;

    private FileConfiguration config = FactionBasics.getInstance().getConfig();

    public AntiWorldDownloaderUtils(FactionBasics instance) {
        this.plugin = instance;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (channel.equals("WDL|INIT") || channel.equals("WDL|CONTROL")) {
            if (!player.hasPermission("bwdl.bypass"))
                sendWDL(player);
        } else if (channel.equals("schematica") &&
                !player.hasPermission("bwdl.bypass")) {
            sendSCH(player);
        }
    }

    public void sendWDL(Player player) {
        byte[] packet = createPacket(false);
        player.sendPluginMessage((Plugin)this.plugin, "WDL|CONTROL", packet);
        final Player p = player;
        Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, new Runnable() {
            public void run() {
                if (AntiWorldDownloaderUtils.this.config.getBoolean("ANTI_MOD.ENABLE")) {
                    String cmd = AntiWorldDownloaderUtils.this.config.getString("ANTI_MOD.PUNISH_COMMAND");
                    cmd = cmd.replaceAll("<player>", p.getName());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), cmd);
                }
            }
        },40L);
    }

    public void sendSCH(Player player) {
        final Player p = player;
        Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, new Runnable() {
            public void run() {
                if (AntiWorldDownloaderUtils.this.config.getBoolean("ANTI_MOD.ENABLE")) {
                    String cmd = AntiWorldDownloaderUtils.this.config.getString("ANTI_MOD.PUNISH_COMMAND");
                    cmd = cmd.replaceAll("<player>", p.getName());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), cmd);
                }
            }
        },40L);
    }

    private byte[] createPacket(boolean allowed) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeInt(0);
        output.writeBoolean(allowed);
        return output.toByteArray();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        if (!p.hasPermission("sbasics.bypass.wdl"))
            Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, new Runnable() {
                public void run() {
                    if (p.getListeningPluginChannels().contains("schematica"))
                        AntiWorldDownloaderUtils.this.sendSCH(p);
                }
            },  40L);
    }
}
