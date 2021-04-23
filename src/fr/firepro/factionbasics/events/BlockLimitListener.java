package fr.firepro.factionbasics.events;

import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.event.Listener;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockLimitListener implements Listener {
    FactionBasics main;

    private FileConfiguration config = FactionBasics.getInstance().getConfig();

    private FileConfiguration messageConfig = (FileConfiguration) FactionBasics.getInstance().getConfigMessage();



    public BlockLimitListener(FactionBasics main) {
        this.main = main;
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Chunk chunk = block.getChunk();
        if (event.isCancelled())
            return;
        if (block.getType().equals(Material.WORKBENCH)) {
            int nomberOftable = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) &&
                                chunkBlock.getType().equals(Material.WORKBENCH))
                            nomberOftable++;
                    }
                }
            }
            if (nomberOftable > this.config.getInt("BLOCKS_LIMIT.CRAFTING_TABLE")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(58).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.CRAFTING_TABLE"))));
            }
        }
        if (block.getType().equals(Material.HOPPER)) {
            int numberOfHooper = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) &&
                                chunkBlock.getType().equals(Material.HOPPER))
                            numberOfHooper++;
                    }
                }
            }
            if (numberOfHooper > this.config.getInt("BLOCKS_LIMIT.HOPPER")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(154).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.HOPPER"))));
            }
        }
        if (block.getType().equals(Material.FURNACE) || block.getType().equals(Material.BURNING_FURNACE)) {
            int numberOfFurnace = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) &&
                                chunkBlock.getType().equals(Material.FURNACE))
                            numberOfFurnace++;
                    }
                }
            }
            if (numberOfFurnace > this.config.getInt("BLOCKS_LIMIT.FURNACE")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(61).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.FURNACE"))));
            }
        }
        if (block.getType().equals(Material.PISTON_BASE) || block.getType().equals(Material.PISTON_STICKY_BASE)) {
            int numberOfPiston = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) && (
                                chunkBlock.getType().equals(Material.PISTON_BASE) || chunkBlock.getType().equals(Material.PISTON_STICKY_BASE)))
                            numberOfPiston++;
                    }
                }
            }
            if (numberOfPiston > this.config.getInt("BLOCKS_LIMIT.PISTON")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(33).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.PISTON"))));
            }
        }


        if (block.getType().equals(Material.DROPPER)) {
            int numberOfDropper = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) && (
                                chunkBlock.getType().equals(Material.DROPPER)))
                            numberOfDropper++;
                    }
                }
            }
            if (numberOfDropper > this.config.getInt("BLOCKS_LIMIT.DROPPER")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(33).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.DROPPER"))));
            }
        }

        if (block.getType().equals(Material.DISPENSER)) {
            int numberOfDispenser = 0;
            for (int x = chunk.getX() * 16; x < chunk.getX() * 16 + 16; x++) {
                for (int z = chunk.getZ() * 16; z < chunk.getZ() * 16 + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block chunkBlock = chunk.getBlock(x, y, z);
                        if (chunkBlock != null && !chunkBlock.getType().equals(Material.AIR) && (
                                chunkBlock.getType().equals(Material.DISPENSER)))
                            numberOfDispenser++;
                    }
                }
            }
            if (numberOfDispenser > this.config.getInt("BLOCKS_LIMIT.DISPENSER")) {
                event.setCancelled(true);
                player.sendMessage(this.messageConfig.getString("BLOCKS_LIMIT.LIMIT_REACHED").replace("<material>", Material.getMaterial(33).name()).replace("<amount>", String.valueOf(this.config.getInt("BLOCKS_LIMIT.DISPENSER"))));
            }
        }
    }
}
