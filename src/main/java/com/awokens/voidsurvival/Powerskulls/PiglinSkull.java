package com.awokens.voidsurvival.Powerskulls;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PiglinSkull implements Listener {

    @EventHandler
    public void change(PlayerArmorChangeEvent event) {

        if (event.getSlotType() != PlayerArmorChangeEvent.SlotType.HEAD) return;

        ItemStack newItem = event.getNewItem();
        Player player = event.getPlayer();

        if (newItem.getType() != Material.PIGLIN_HEAD) return;

        player.playSound(
                player, Sound.ENTITY_PIGLIN_AMBIENT, 1.0F, 1.0F);

    }

    @EventHandler
    public void attack(EntityDamageByEntityEvent event) {

        if (event.isCancelled()) return;

        if (!(event.getEntity() instanceof Player)) return;

        if (!(event.getDamager() instanceof Player attacker)) return;


        ItemStack helmet = attacker.getInventory().getHelmet();

        if (helmet == null || helmet.getType() != Material.PIGLIN_HEAD) return;

        double damage = event.getDamage();

        damage += damage * 1.5;

        event.setDamage(damage);
    }

    @EventHandler
    public void victim(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack helmet = player.getInventory().getHelmet();

        if (helmet == null || helmet.getType() != Material.PIGLIN_HEAD) return;

        player.playSound(player.getLocation(), Sound.ENTITY_PIGLIN_HURT, 1.0F, 1.0F);

    }
}
