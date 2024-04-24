package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.Manager.TNTTrailManager;
import com.awokens.voidsurvival.VoidSurvival;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;

public class FireballProjectile implements Listener {

    private final VoidSurvival plugin;
    public FireballProjectile(VoidSurvival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void projectile(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        if (!(projectile instanceof Fireball fireball)) return; // is a fireball check #1

        if (!fireball.hasMetadata("fireball")) return; // is a fireball check #2

        ProjectileSource source = projectile.getShooter();

        if (!(source instanceof Player player)) return; // wasn't shot by a player check #3

        Block block = event.getHitBlock();

        if (block == null) return;

        ItemStack helmet = player.getInventory().getHelmet();

        int multiplier = 1;
        if (helmet != null && helmet.getType() == Material.CREEPER_HEAD) {
            multiplier = 2;
        }

        new TNTTrailManager(plugin, block, 120 * multiplier);
    }

    @EventHandler
    public void launch(PlayerInteractEvent event) {

        if (event.getAction().isLeftClick()) return;
        if (!event.hasItem()) return;

        ItemStack heldItem = event.getItem();

        if (heldItem == null) return;

        if (heldItem.getType() != Material.FIRE_CHARGE) return;

        if (!heldItem.displayName().toString().contains("Fireball")) return; // not a throwable fireball

        event.setCancelled(true);

        Player player = event.getPlayer();

        if (!player.isSneaking()) return;

        if (player.getCooldown(Material.FIRE_CHARGE) > 0) {
            return;
        }

        heldItem.subtract(1);

        player.setCooldown(Material.FIRE_CHARGE, 20 * 5);

        Fireball fireball = player.launchProjectile(LargeFireball.class);
        fireball.setGlowing(true);
        fireball.setMetadata("fireball", new FixedMetadataValue(plugin, true));
        player.playSound(player, Sound.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
    }

}
