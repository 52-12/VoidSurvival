package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.VoidSurvival;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSaddleRidePlayer implements Listener {

    private final VoidSurvival plugin;
    public PlayerSaddleRidePlayer(VoidSurvival plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void interact(PlayerInteractAtEntityEvent event) { // mount player on clicked player

        Player player = event.getPlayer();

        if (!player.isSneaking()) return;

        Entity entity = event.getRightClicked();

        if (!(entity instanceof Player who)) return;

        ItemStack helmet = who.getInventory().getHelmet();

        if (helmet == null || helmet.getType() != Material.SADDLE) return;

        if (player.getCooldown(Material.SADDLE) > 0) return;

        player.setSneaking(false);
        player.setCooldown(Material.SADDLE, 20); // 1 second cooldown

        new BukkitRunnable() {
            @Override
            public void run() {
                who.addPassenger(player);
                player.playSound(
                        player.getLocation(),
                        Sound.ITEM_ARMOR_EQUIP_GENERIC,
                        1.0F,
                        1.0F
                );
            }
        }.runTaskLater(plugin, 1L);
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) { // eject passengers riding player upon sneaking

        Player player = event.getPlayer();

        if (player.getCooldown(Material.SADDLE) > 0) return;

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }

    @EventHandler
    public void unequip(PlayerArmorChangeEvent event) { // eject passengers riding player upon unequipped saddle

        if (event.getSlotType() != PlayerArmorChangeEvent.SlotType.HEAD) return;

        Player player = event.getPlayer();

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }

    @EventHandler
    public void swim(EntityToggleSwimEvent event) { // eject passengers riding player upon da wata

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null || helmet.getType() != Material.SADDLE) return;

        if (!event.isSwimming() || !player.isInWater()) return;

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }
}
