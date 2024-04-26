package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LavaCauldronMechanism implements Listener {


    private final VoidSurvival plugin;
    public LavaCauldronMechanism(VoidSurvival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {

        Block clickdBlock = event.getClickedBlock();

        if (event.getAction().isLeftClick()) return;

        if (!event.getPlayer().isSneaking()) return;

        if (clickdBlock == null || clickdBlock.getType() != Material.CAULDRON) return;

        Block below = clickdBlock.getRelative(BlockFace.DOWN);

        if (below.getType() != Material.CAMPFIRE) return;

        org.bukkit.block.data.type.Campfire campfire = (org.bukkit.block.data.type.Campfire) below.getBlockData();

        if (!campfire.isLit()) return;

        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.isEmpty()) return;

        if (heldItem.getType() != Material.COBBLED_DEEPSLATE) return;

        if (heldItem.getAmount() < 16) return;

        if (player.getCooldown(Material.COBBLED_DEEPSLATE) > 0) return;

        player.setCooldown(Material.COBBLED_DEEPSLATE, 20 * 181);

        heldItem.subtract(32);
        player.swingMainHand();
        player.playSound(clickdBlock.getLocation(), Sound.ITEM_BUNDLE_INSERT, 1.0F, 1.0F);
        player.playSound(clickdBlock.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0F, 1.0F);


        final int[] counters = {3600, 0};

        new BukkitRunnable() {
            @Override
            public void run() {

                Block block = clickdBlock.getLocation().getBlock();

                if (counters[0] < 1) {
                    //	set clicked block to lava cauldron
                    block.setType(Material.LAVA_CAULDRON);
                    this.cancel();
                    return;
                }

                if (block.getType() != Material.CAULDRON) {
                    this.cancel();
                }

                Block down = block.getRelative(BlockFace.DOWN);

                if (down.getType() != Material.CAMPFIRE) this.cancel();

                org.bukkit.block.data.type.Campfire campfire = (org.bukkit.block.data.type.Campfire) down.getBlockData();

                if (!campfire.isLit()) this.cancel();

                if (this.isCancelled()) return;

                if (counters[1] > 20) {
                    Particle.DustTransition transition = new Particle.DustTransition(
                            Color.ORANGE,
                            Color.RED,
                            5
                    );
                    Location location = block.getLocation().clone().toCenterLocation();
                    location.add(0, 0.2, 0);
                    location.offset(0, 1, 0);

                    location.getWorld().spawnParticle(Particle.REDSTONE, location, 5, transition);
                    location.getWorld().playSound(location, Sound.BLOCK_LAVA_AMBIENT, 1.0F, 1.0F);

                    Random random = new Random();

                    // Generating a random number between 0 and 99
                    int randomNumber = random.nextInt(100);

                    if (randomNumber <= 25) {
                        location.getWorld().playSound(location, Sound.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
                    }
                    counters[1] = 0;
                }

                counters[1] += 1;
                counters[0] -= 1;

            }
        }.runTaskTimer(plugin, 0L, 1L);

    }
}
