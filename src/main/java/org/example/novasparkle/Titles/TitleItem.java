package org.example.novasparkle.Titles;

import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.example.novasparkle.Configs.Config;
import org.example.novasparkle.LunaMoonTitles;
import org.example.novasparkle.Menus.Menu;
import org.example.novasparkle.Utils.Utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class TitleItem {
    @Setter
    private Material material;
    private final String displayName;
    @Getter
    private final String tag;
    @Getter
    private boolean opened;
    private ItemStack itemStack;

    public TitleItem(String tag, String prefix) {
        this.displayName = Config.color(prefix);
//        this.lores = section.getStringList("lore").stream().map(Config::color).collect(Collectors.toList());
        this.tag = tag;
    }
    public void checkOpened(Player player) {
        this.opened = (player.hasPermission("LMTitles.title." + this.tag));
        this.material = Menu.getTitleConfig().getMaterial(this.opened);
        List<String> lores = Menu.getTitleConfig().getLore(this.opened);
        this.itemStack = new ItemStack(this.material);
        NBT.modify(this.itemStack, nbt -> {
            nbt.setString("tag", this.tag);
        });
        Utils.setSettings(this.itemStack, this.displayName, lores);
    }
    public void apply(Player player) {
        CommandSender sender = LunaMoonTitles.getINSTANCE().getServer().getConsoleSender();
        Bukkit.dispatchCommand(sender, String.format("lp user %s meta removesuffix 1", player.getName()));
        Bukkit.dispatchCommand(sender, String.format("lp user %s meta setsuffix 1 %s", player.getName(), this.displayName.trim()));
        player.sendMessage(Config.getMessage("titleApplied").replace("[title]", this.displayName));
    }

    public void set(Inventory inventory, int slot) {
        inventory.setItem(slot, this.itemStack);
    }

    @Override
    public String toString() {
        return "TitleItem{" +
                "displayName='" + displayName + '\'' +
                ", tag='" + tag + '\'' +
                ", opened=" + opened +
                '}';
    }
}
