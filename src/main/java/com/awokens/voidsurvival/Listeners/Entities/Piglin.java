package com.awokens.voidsurvival.Listeners.Entities;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import com.awokens.voidsurvival.VoidSurvival;
import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Random;

public class Piglin implements Listener {


    private final VoidSurvival plugin;
    public Piglin(VoidSurvival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void barter(PiglinBarterEvent event) {

        Iterator<ItemStack> outcome = event.getOutcome().iterator();

        while (outcome.hasNext()) {
            ItemStack item = outcome.next();
            switch (item.getType()) {
                case OBSIDIAN, CRYING_OBSIDIAN -> outcome.remove();
            }
        }
    }
    @EventHandler
    public void spawn(PreCreatureSpawnEvent event) {


        EntityType type = event.getType();

        if (type != EntityType.PIGLIN) return;

        Location location = event.getSpawnLocation();

        if (!location.getWorld().getName().equalsIgnoreCase(SpawnPointManager.getNetherSpawn().getWorld().getName())) return;

        if (event.getReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractAtEntityEvent event) {

        if (!(event.getRightClicked() instanceof PigZombie zombifiedPiglin)) return;

        if (!zombifiedPiglin.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) return;

        if (!zombifiedPiglin.isAdult()) return;

        Player player = event.getPlayer();

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() != Material.ENCHANTED_GOLDEN_APPLE) return;

        heldItem.subtract(1);

        final int[] cureRateByTick = {new Random().nextInt(2000, 3600)};

        zombifiedPiglin.getWorld().playSound(
                zombifiedPiglin, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.0F);

        BukkitRunnable result = new BukkitRunnable() {
            @Override
            public void run() {
                zombifiedPiglin.setInvisible(true);
                org.bukkit.entity.Piglin piglin = (org.bukkit.entity.Piglin) zombifiedPiglin.getWorld().spawnEntity(
                        zombifiedPiglin.getLocation(), EntityType.PIGLIN, CreatureSpawnEvent.SpawnReason.CUSTOM);
                piglin.setImmuneToZombification(true);
                piglin.setAdult();
                piglin.getWorld().playSound(piglin, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
                zombifiedPiglin.remove();
            }
        };

        Particle.DustOptions options = new Particle.DustOptions(Color.WHITE, 5);

        new BukkitRunnable() {
            @Override
            public void run() {

                if (zombifiedPiglin.isDead()) {
                    this.cancel();
                    return;
                }

                if (cureRateByTick[0] < 1) {
                    // task is complete
                    result.runTask(plugin);
                    this.cancel();
                    return;
                }

                cureRateByTick[0] -= 1;
                zombifiedPiglin.getWorld().spawnParticle(
                        Particle.REDSTONE,
                        zombifiedPiglin.getLocation(),
                        1,
                        options
                );
            }
        }.runTaskTimer(plugin, 0L, 1L);

    }

}
