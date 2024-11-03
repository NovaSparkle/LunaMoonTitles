package org.example.novasparkle.Menus.Items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.example.novasparkle.Utils.Utils;

import java.util.Objects;

public class Item {
    @Getter
    protected final ItemStack itemStack;
    @Setter
    protected byte slot;

    public Item(ConfigurationSection section, int slot) {
        Material material = Material.getMaterial(Objects.requireNonNull(section.getString("material")));
        assert material != null;
        this.itemStack = new ItemStack(material);
        this.slot = (byte) slot;
        Utils.setSettings(this.itemStack, section.getString("displayName"), section.getStringList("lore"));
    }
    public Item(ConfigurationSection section) {
        Material material = Material.getMaterial(Objects.requireNonNull(section.getString("material")));
        assert material != null;
        this.itemStack = new ItemStack(material);
        Utils.setSettings(this.itemStack, section.getString("displayName"), section.getStringList("lore"));
    }
    public void set(Inventory inventory) {
        inventory.setItem(this.slot, this.itemStack);
    }
}
