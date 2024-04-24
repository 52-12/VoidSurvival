package com.awokens.voidsurvival.Manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TNTTrailManager {

    // blocks that connected between other related blocks
    // if obsidian block is in between a -> b. Then we cannot
    // reach b as there is a correlative block, which is the obsidian block.
    // But any other surrounding blocks can be reached.
    private final Set<Location> relatives;

    private float counter;

    private final Plugin plugin;


    private final float max_counter = 600.0F;

    public TNTTrailManager(Plugin plugin, Block startBlock, int counter) {
        this.relatives = new HashSet<>();
        this.plugin = plugin;
        this.counter = Math.min(counter, max_counter);

        if (isRelative(startBlock)) {
            startBlock.breakNaturally(true);
        }
        trail(startBlock);

    }

    public static List<Block> getConnectedBlocks(Block next) {
        List<Block> neighbors = new ArrayList<>();

        // Get all 6 blocks that are neighbors of the next block
        neighbors.add(next.getRelative(BlockFace.NORTH));
        neighbors.add(next.getRelative(BlockFace.SOUTH));
        neighbors.add(next.getRelative(BlockFace.WEST));
        neighbors.add(next.getRelative(BlockFace.EAST));
        neighbors.add(next.getRelative(BlockFace.UP));
        neighbors.add(next.getRelative(BlockFace.DOWN));
        return neighbors;
    }


    public static boolean isRelative(Block block) {
        if (!block.isSolid()) return false; // e.g. short grass, planets, etc.
        double hardness = block.getType().getHardness();
        return !(hardness < 0.0F);
    }

    private void trail(Block next) {

        for (Block connectedBlock : getConnectedBlocks(next)) {

            if (this.counter <= 0.0f || this.counter > max_counter) break;

            if (!isRelative(connectedBlock)) continue;

            Location location = connectedBlock.getLocation();

            if (this.relatives.contains(location)) continue;

            float tier = connectedBlock.getType().getHardness();

            if (this.counter - tier <= 0.0) return;

            connectedBlock.breakNaturally(true);

            location.getWorld().playSound(
                    location, Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
            location.getWorld().playSound(
                    location, Sound.ITEM_DYE_USE, 0.5F, 0.5F);

            this.relatives.add(location);

            this.counter -= tier;

            new BukkitRunnable() {
                @Override
                public void run() {
                    trail(connectedBlock);
                }
            }.runTaskLater(plugin, 5L);
        }
    }
}
