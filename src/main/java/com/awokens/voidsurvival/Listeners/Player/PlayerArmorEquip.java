package com.awokens.voidsurvival.Listeners.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerArmorEquip implements Listener {


    @EventHandler
    public void hotbar_equip(PlayerInteractEvent event) {

        if (!event.getAction().isRightClick()) return;

        if (!event.hasItem()) return;

        ItemStack currentItem = event.getItem();

        if (currentItem == null) return;

        if (!isDiamondArmorPiece(currentItem)) return;

        Player player = event.getPlayer();

        EquipmentSlot equipmentSlot = currentItem.getType().getEquipmentSlot();

        
        ItemStack destinatedItem;
        int slot = switch (equipmentSlot) {
            case HEAD -> 39;
            case CHEST -> 38;
            case LEGS -> 37;
            case FEET -> 36;
            default -> -1;
        };
        destinatedItem = player.getInventory().getItem(slot);

        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor == null) continue;

            if (destinatedItem != null && isDiamondArmorPiece(destinatedItem)) {
                if (armor.isSimilar(destinatedItem)) {
                    return;
                }
            }

            if (isDiamondArmorPiece(armor)) {
                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler
    public void hotbar_swap(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getType() != InventoryType.CRAFTING) return;

        if (event.getAction() != InventoryAction.HOTBAR_SWAP) return;

        int button = event.getHotbarButton();

        if (button == -1) {
            button = 40;
        }

        ItemStack currentItem = player.getInventory().getItem(button);
        if (currentItem == null) return; // is air

        if (!isDiamondArmorPiece(currentItem)) return; // also not diamond piece

        switch (event.getSlot()) { // not swapping with armor
            case 36, 37, 38, 39:
                break;
            default:
                return;
        }

        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor == null) continue;

            if (armor.isSimilar(currentItem)) {
                return;
            }

            if (isDiamondArmorPiece(armor)) {
                event.setCancelled(true);
                return;

            }
        }

    }

    @EventHandler
    public void dispense(BlockDispenseArmorEvent event) {

        if (!(event.getTargetEntity() instanceof Player player)) return;

        if (!isDiamondArmorPiece(event.getItem())) return;

        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor == null) continue;

            if (armor.isSimilar(event.getItem())) return;

            if (isDiamondArmorPiece(armor)) {
                event.setCancelled(true);

                return;
            }
        }

    }

    @EventHandler
    public void cursor(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getType() != InventoryType.CRAFTING) return;

        switch (event.getAction()) { // cursor related action
            case PLACE_ALL:
            case SWAP_WITH_CURSOR:
                break;
            default:
                return;
        }

        if (event.getSlotType() != InventoryType.SlotType.ARMOR) return;

        ItemStack cursorItem = event.getCursor();

        ItemStack currentItem = event.getCurrentItem();

        if (!isDiamondArmorPiece(cursorItem)) return;

        if (currentItem == null) return;

        if (isDiamondArmorPiece(cursorItem) && isDiamondArmorPiece(currentItem)) {
            return; // allow swap if both are diamond pieces
        }

        for (ItemStack armor : player.getInventory().getArmorContents()) {

            if (armor == null) continue;

            if (isDiamondArmorPiece(armor)) {

                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler
    public void drag(InventoryDragEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getType() != InventoryType.CRAFTING) return;

        if (event.getType() != DragType.EVEN) return; // not armor

        ItemStack cursor = event.getOldCursor();

        if (event.getRawSlots().size() > 1) return; // not armor

        int slot = event.getRawSlots().iterator().next();

        switch (slot) {
            case 5, 6, 7, 8:
                break;
            default:
                return;
        }

        for (ItemStack armor : player.getInventory().getArmorContents()) {

            if (armor == null) continue;

            if (armor.isSimilar(cursor)) return;

            if (isDiamondArmorPiece(armor)) {
                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler
    public void instant_move(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getType() != InventoryType.CRAFTING) return;

        if (event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;

        if (!isDiamondArmorPiece(currentItem)) return;

        if (!canEquip(currentItem, player)) return; // didn't equip the moved item piece

        for (ItemStack armor : player.getInventory().getArmorContents()) {

            if (armor == null) continue;

            if (armor.isSimilar(currentItem)) return;

            if (isDiamondArmorPiece(armor)) {
                event.setCancelled(true);
                return;
            }
        }

    }

    public boolean canEquip(ItemStack item, Player player) {

        EquipmentSlot equipmentSlot = item.getType().getEquipmentSlot();

        int slot = switch (equipmentSlot) {
            case FEET -> 36;
            case LEGS -> 37;
            case CHEST -> 38;
            case HEAD -> 39;
            default -> -1;
        };

        if (slot == -1) return false; // not an armor slot, we ignore it

        ItemStack armorItem = player.getInventory().getItem(slot);

        return armorItem == null || armorItem.getType().isAir();

    }

    public boolean isDiamondArmorPiece(ItemStack item) {

        return switch (item.getType()) {
            case DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET -> true;
            default -> false;
        };
    }

}
