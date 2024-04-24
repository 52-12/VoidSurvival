package com.awokens.voidsurvival.Listeners.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void apple(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (!(block.getBlockData() instanceof Leaves)) return;

        Random random = new Random();

        if (random.nextInt(100) <= 92) {
            return;
        }

        event.setDropItems(false);
        block.getLocation().getWorld().dropItemNaturally(block.getLocation().toBlockLocation(),
                new ItemStack(Material.APPLE));

    }
}
