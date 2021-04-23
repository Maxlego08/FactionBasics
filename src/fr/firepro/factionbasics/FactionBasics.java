package fr.firepro.factionbasics;

import fr.firepro.factionbasics.commands.*;
import fr.firepro.factionbasics.events.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FactionBasics extends JavaPlugin {
    public Boolean chatStatue;
    public static FactionBasics instance;
    private File configMessageFile = new File(getDataFolder(), "messages.yml");
    private YamlConfiguration configMessage;

    public YamlConfiguration getConfigMessage() {
        return this.configMessage;
    }

    @Override
    public void onEnable() {
        chatStatue = true;
        instance = this;
        saveDefaultConfig();
        loadMessageYaml();

        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("site").setExecutor(new SiteCommand());
        this.getCommand("vote").setExecutor(new VoteCommand());

        this.getCommand("bottle").setExecutor(new BottleXPCommand(this));
        this.getCommand("maintenance").setExecutor(new MaintenanceCommand(this));

        this.getServer().getPluginManager().registerEvents(new BottleXPCommand(this), this);

        if (this.getConfig().getBoolean("poubelle.enable")) {
            getCommand("poubelle").setExecutor((CommandExecutor) new Poubelle());
        }
        this.getServer().getPluginManager().registerEvents(new Maintenance(this), this);

        this.getServer().getPluginManager().registerEvents(new AntiSpam(), this);
        this.getServer().getPluginManager().registerEvents(new BlockLimitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new CooldownPearl(), this);

        this.getServer().getPluginManager().registerEvents(new MobDrop(), this);
        this.getServer().getPluginManager().registerEvents(new ChatCommand(), this);

        if (this.getConfig().getBoolean("dynamites")) {
            this.getServer().getPluginManager().registerEvents((Listener) new DynamiteEvents(this), (Plugin) this);
            this.getCommand("dynamite").setExecutor((CommandExecutor) new DynamiteCommand(this));
        }
    }


    @Override
    public void onDisable() {
    }

    public static FactionBasics getInstance() {
        return instance;
    }

    public Boolean getChatStatue() {
        return chatStatue;
    }

    public void setChatStatue(Boolean chatStatue) {
        this.chatStatue = chatStatue;
    }

    public void loadMessageYaml() {
        if (!this.configMessageFile.exists())
            saveResource("messages.yml", false);
        this.configMessage = YamlConfiguration.loadConfiguration(this.configMessageFile);
    }
}
