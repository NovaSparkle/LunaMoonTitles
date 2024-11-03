package org.example.novasparkle.Menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryManager {
    private static final HashMap<Inventory, IMenu> activeInventories = new HashMap<>();
    public static void openInventory(Player player, Menu menu) {
        register(menu.getInventory(), menu);
        player.openInventory(menu.getInventory());
    }
    public static void register(Inventory inventory, IMenu handler) {
        activeInventories.put(inventory, handler);
    }
    public static void unregister(Inventory inventory) {
        activeInventories.remove(inventory);
    }
    public static void handleOpen(InventoryOpenEvent event) {
        IMenu handler = activeInventories.get(event.getInventory());
        if (handler != null) {
            handler.onOpen(event);
        }
    }
    public static void handleClick(InventoryClickEvent event) {
        IMenu handler = activeInventories.get(event.getInventory());
        if (handler != null) {
            handler.onClick(event);
        }
    }
    public static void handleClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        IMenu handler = activeInventories.get(event.getInventory());
        if (handler != null) {
            handler.onClose(event);
            unregister(inventory);
        }

    }
}
