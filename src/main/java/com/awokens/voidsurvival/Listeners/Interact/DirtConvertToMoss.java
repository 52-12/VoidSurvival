package com.awokens.voidsurvival.Listeners.Interact;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class DirtConvertToMoss implements Listener {

    @EventHandler
    public void moss(PlayerInteractEvent event) {

        if (event.getAction().isLeftClick()) return;

        Block block = event.getClickedBlock();

        if (block == null) return;


        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() != Material.BONE_MEAL) return;

        switch (block.getType()) {
            case DIRT:
                block.setType(Material.MOSS_BLOCK);
                break;
            case ROOTED_DIRT:
                block.setType(Material.GRASS_BLOCK);
                break;
            case DEAD_BUSH:
                block.applyBoneMeal(BlockFace.UP);
                int randomIndex = new Random().nextInt(treeSaplingTypes.length);
                block.setType(treeSaplingTypes[randomIndex]);
                return;
            default:
                return;
        }
        heldItem.subtract(1);
        block.applyBoneMeal(BlockFace.UP);
    }

    public Material[] treeSaplingTypes = {
            Material.OAK_SAPLING,
            Material.BIRCH_SAPLING,
            Material.DARK_OAK_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.ACACIA_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.BIRCH_SAPLING,
    };
}
