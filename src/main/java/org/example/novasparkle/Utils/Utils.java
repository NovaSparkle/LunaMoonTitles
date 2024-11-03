package org.example.novasparkle.Utils;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.novasparkle.Configs.Config;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class Utils {
    public String toStr(int integer) {
        return String.valueOf(integer);
    }
    public int toInt(String string) {
        return Integer.parseInt(string);
    }
//    public static ItemStack setItemName(ItemStack item, String name) {
//        ItemMeta meta = item.getItemMeta();
//        meta.setDisplayName(Config.color(name));
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        item.setItemMeta(meta);
//        return item;
//    }
    public static ItemStack setSettings(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.color(name));
        meta.setLore(lore.stream().map(Config::color).collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
    public static int getIndex(int row, int col) {
        return (row - 1) * 9 + col - 1;
    }

//    public static void setItemLore(ItemStack item, List<String> lores) {
//        ItemMeta meta = item.getItemMeta();
//        meta.setLore(lores.stream().map(Config::color).collect(Collectors.toList()));
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        item.setItemMeta(meta);
//    }
}
