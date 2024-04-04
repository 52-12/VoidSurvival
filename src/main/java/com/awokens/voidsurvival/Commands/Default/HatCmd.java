package com.awokens.voidsurvival.Commands.Default;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;

public class HatCmd {

    public HatCmd() {
        new CommandAPICommand("hat")
                .executesPlayer((player, args) -> {

                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    ItemStack helmet = player.getInventory().getHelmet();

                    if (helmet == null) {
                        helmet = new ItemStack(Material.AIR);
                    }

                    if (helmet.isEmpty() && heldItem.isEmpty()) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<yellow>You have nothing to swap hats with."
                        ));
                        return;
                    }

                    if (player.getCooldown(helmet.getType()) > 0
                            || player.getCooldown(heldItem.getType()) > 0) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<yellow>Slow down with the hats buddy"
                        ));
                        return;
                    }

                    heldItem = heldItem.clone();
                    helmet = helmet.clone();

                    for (ItemStack armor : player.getInventory().getArmorContents()) {
                        if (armor == null) continue;
                        if (armor.isSimilar(helmet)) continue;

                        switch (armor.getType()) {
                            case DIAMOND_HELMET:
                            case DIAMOND_CHESTPLATE:
                            case DIAMOND_LEGGINGS:
                            case DIAMOND_BOOTS:
                                player.sendMessage(MiniMessage.miniMessage().deserialize(
                                        "<yellow>You cannot equip more than 1 diamond piece."
                                ));
                                return;
                        }
                    }

                    player.setCooldown(heldItem.getType(), 20);
                    player.setCooldown(helmet.getType(), 20);

                    player.getInventory().setHelmet(heldItem);
                    player.getInventory().setItemInMainHand(helmet);

                    player.playSound(player, Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);

                }).register();
    }
}
