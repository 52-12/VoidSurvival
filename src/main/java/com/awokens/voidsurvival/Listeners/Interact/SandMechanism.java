package com.awokens.voidsurvival.Listeners.Interact;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SandMechanism implements Listener {

    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(4);

        if (block == null) return;

        switch (block.getType()) {
            case CLAY, MUD -> event.setCancelled(true);
        }

    }

    @EventHandler
    public void dirt(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.DIRT) return;

        ItemStack used = event.getItem();

        if (used == null || used.getType() != Material.POTION) return;

        Player player = event.getPlayer();

        if (player.getCooldown(Material.GLASS_BOTTLE) > 0) {
            return;
        }

        player.setCooldown(Material.GLASS_BOTTLE, 5);
        player.playSound(block.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
        player.playSound(block.getLocation(), Sound.ITEM_DYE_USE, 0.5F, 0.5F);

    }

    @EventHandler
    public void mud(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.MUD) return;

        ItemStack used = event.getItem();
        if (used == null || used.getType() != Material.POTION) return;

        Player player = event.getPlayer();
        if (player.getCooldown(Material.GLASS_BOTTLE) > 0) {
            return;
        }

        player.setCooldown(Material.GLASS_BOTTLE, 5);

        used.setType(Material.GLASS_BOTTLE);
        block.setType(Material.CLAY);

        player.playSound(player, Sound.ITEM_BOTTLE_EMPTY, 1.0F, 1.0F);
        player.playSound(block.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
        player.playSound(block.getLocation(), Sound.ITEM_DYE_USE, 0.5F, 0.5F);
        player.swingMainHand();

    }

    @EventHandler
    public void clay(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.CLAY) return;

        ItemStack used = event.getItem();
        if (used == null || used.getType() != Material.POTION) return;

        Player player = event.getPlayer();
        if (player.getCooldown(Material.GLASS_BOTTLE) > 0) {
            return;
        }

        player.setCooldown(Material.GLASS_BOTTLE, 5);

        used.setType(Material.GLASS_BOTTLE);

        Random random = new Random();

        if (random.nextInt(100) <= 40) {
            block.setType(Material.SUSPICIOUS_SAND);
        } else {
            block.setType(Material.SAND);
        }
        player.playSound(player, Sound.ITEM_BOTTLE_EMPTY, 1.0F, 1.0F);
        player.playSound(block.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
        player.playSound(block.getLocation(), Sound.ITEM_DYE_USE, 0.5F, 0.5F);
        player.swingMainHand();
    }
}
