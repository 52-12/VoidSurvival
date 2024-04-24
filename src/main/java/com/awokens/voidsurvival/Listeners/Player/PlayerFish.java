package com.awokens.voidsurvival.Listeners.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class PlayerFish implements Listener {

    @EventHandler
    public void fish(PlayerFishEvent event) {

        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();

        if (state == PlayerFishEvent.State.BITE) {
            player.playSound(player, Sound.ENTITY_PLAYER_SPLASH, 0.5F, 1.0F);

            Title title = Title.title(
                    Component.text(""),
                    MiniMessage.miniMessage().deserialize("<bold><gradient:#1A75D3:#1C87F6:#1A75D3>\uD83C\uDFA3  BITE!")
            );
            player.showTitle(title);
            return;
        }

        if (state != PlayerFishEvent.State.CAUGHT_FISH) return;

        player.playerListName(MiniMessage.miniMessage().deserialize(
                "<white>" + player.getName() + "<yellow> " + player.getStatistic(Statistic.FISH_CAUGHT)
        ));

        Location head = player.getEyeLocation();
        Vector direction = head.getDirection();
        Location front = head.clone().add(direction.multiply(1));

        player.spawnParticle(Particle.SONIC_BOOM, front, 1);

        Random random = new Random();

        double probability = 0.01D;
        double randomNumber = random.nextDouble();

        if (randomNumber > probability) return;

        EntityType[] entityTypes = {
                EntityType.DROWNED,
                EntityType.GUARDIAN,
                EntityType.ELDER_GUARDIAN
        };

        int randomIndex = random.nextInt(entityTypes.length);

        EntityType entityType = entityTypes[randomIndex];
        Location hook = event.getHook().getLocation();

        Entity entity = hook.getWorld().spawnEntity(
                hook, entityType, CreatureSpawnEvent.SpawnReason.CUSTOM);


        entity.getWorld().playSound(
                hook, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0F, 1.0F);

        entity.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, hook, 1);
    }
}
