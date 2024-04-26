package com.awokens.voidsurvival.Listeners.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void ironNugget(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.STONE)  return;

        if (ThreadLocalRandom.current().nextInt(100) <= 80) {
            return;
        }

        event.setDropItems(false);

        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
        int fortuneLevel = 1 + heldItem.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

        int bonus = ThreadLocalRandom.current().nextInt(1, fortuneLevel);

        block.getLocation().getWorld().dropItemNaturally(block.getLocation().toBlockLocation(),
                new ItemStack(Material.IRON_NUGGET, bonus));

    }

    @EventHandler
    public void apple(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (!(block.getBlockData() instanceof Leaves)) return;

        if (ThreadLocalRandom.current().nextInt(100) <= 92) {
            return;
        }

        event.setDropItems(false);
        block.getLocation().getWorld().dropItemNaturally(block.getLocation().toBlockLocation(),
                new ItemStack(Material.APPLE));

    }
}
