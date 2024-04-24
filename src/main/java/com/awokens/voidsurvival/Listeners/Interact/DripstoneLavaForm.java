package com.awokens.voidsurvival.Listeners.Interact;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;

public class DripstoneLavaForm implements Listener {

    @EventHandler
    public void dripstone(CauldronLevelChangeEvent event) {

        if (event.getReason() != CauldronLevelChangeEvent
                .ChangeReason.NATURAL_FILL) return;
        
        if (event.getNewState().getType() != Material.LAVA_CAULDRON) return;

        event.setCancelled(true);
    }
}
