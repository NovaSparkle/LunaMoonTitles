package org.example.novasparkle.Menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class Events implements Listener {
    @EventHandler
    private void onOpen(InventoryOpenEvent e) {
        InventoryManager.handleOpen(e);
    }
    @EventHandler
    private void onClick(InventoryClickEvent e) {
        InventoryManager.handleClick(e);
    }
    @EventHandler
    private void onClose(InventoryCloseEvent e) {
        InventoryManager.handleClose(e);
    }
}
