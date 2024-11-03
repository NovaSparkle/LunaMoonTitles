package org.example.novasparkle.Titles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.example.novasparkle.Configs.Config;
import org.example.novasparkle.Menus.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Decoration {
    private final List<Item> items = new ArrayList<>();
    public Decoration() {
        ConfigurationSection section = Config.getSection("menu.decoration");
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            assert itemSection != null;
            for (int slot : itemSection.getIntegerList("slots")) {
                items.add(new Item(itemSection, slot));
            }
        }
    }
    public void set(Inventory inventory) {
        items.forEach(i -> i.set(inventory));
    }
    public byte getSlots() {
        return (byte) this.items.size();
    }
}
