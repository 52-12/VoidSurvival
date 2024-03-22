package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.Manager.TNTTrailManager;
import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

    @EventHandler
    public void explode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed tnt)) return;

        event.setCancelled(true);

        for (Block connectedBlock : TNTTrailManager.getConnectedBlocks(tnt.getLocation().getBlock())) {
            if (TNTTrailManager.isRelative(connectedBlock)) {
                new TNTTrailManager(VoidSurvival.getPlugin(), connectedBlock, 60);
                break;
            }
        }
    }
}
