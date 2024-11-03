package org.example.novasparkle.Menus;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.example.novasparkle.Configs.Config;
import org.example.novasparkle.Configs.TitleConfig;
import org.example.novasparkle.Menus.Items.SwitchButton;
import org.example.novasparkle.Titles.Decoration;
import org.example.novasparkle.Titles.TitleItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.novasparkle.Configs.Config.color;

@SuppressWarnings("deprecation")
public class Menu implements IMenu {
    private static List<TitleItem> allTitles;
    @Getter
    private static TitleConfig titleConfig;

    private final List<List<TitleItem>> partedTitles = new ArrayList<>();
    @Getter
    private final Player holder;
    @Getter
    private final Inventory inventory;
    private final Decoration decoration;
    @Getter
    @Setter
    private byte page;
    @Setter
    private List<TitleItem> currentPage;
    private final List<SwitchButton> switchers = new ArrayList<>();
    public Menu(Player holder, byte page) {
        this.holder = holder;
        this.inventory = Bukkit.createInventory(null,
                                                    Config.getInt("menu.size"),
                                                    color(Config.getString("menu.title")));
        this.decoration = new Decoration();
        byte step = (byte) (this.inventory.getSize() - this.decoration.getSlots());
        for (int i = 0; i < allTitles.size(); i += step) {
            this.partedTitles.add(allTitles.subList(i, Math.min(i + step, allTitles.size())));
        }
        this.page = page;
        this.setCurrentPage(this.partedTitles.get(this.page - 1));
    }
    public void reload() {
        this.currentPage.forEach(t -> t.checkOpened(this.holder));
        List<TitleItem> sortedList = this.currentPage.stream().sorted(Comparator.comparing(TitleItem::isOpened).reversed()).collect(Collectors.toList());
        Iterator<TitleItem> iter = sortedList.iterator();
        int slot = 0;
        while (iter.hasNext()) {
            if (this.getInventory().getItem(slot) == null) {
                TitleItem title = iter.next();
                title.set(this.inventory, slot);
            }
            slot++;
        }
    }
    public void insertButtons() {
        ConfigurationSection section = Config.getSection("menu.buttons");
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            SwitchButton btn = new SwitchButton(itemSection);
            NBT.modify(btn.getItemStack(), nbt -> {
                nbt.setString("switch", btn.getAction());
            });
            this.switchers.add(btn);
            btn.set(this.inventory);
        }
    }
    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.decoration.set(this.inventory);
        this.insertButtons();
        this.reload();

    }
    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null) {
            ReadableNBT nbt = NBT.readNbt(clickedItem);
            if (nbt.hasTag("tag")) {
                TitleItem clickedTitle = this.currentPage.stream().filter(titleItem -> titleItem.getTag().equals(nbt.getString("tag"))).findFirst().orElse(null);
                assert clickedTitle != null;
                if (clickedTitle.isOpened()) {
                    clickedTitle.apply(this.holder);
                } else {
                    this.holder.sendMessage(Config.getMessage("locked"));
                }
            } else if (nbt.hasTag("switch")){
                SwitchButton btn = this.switchers.stream().filter(switcher -> switcher.getAction().equals(nbt.getString("switch"))).findFirst().orElse(null);
                assert btn != null;
                btn.click(this);
            }
        }
    }
    @Override
    public void onClose(InventoryCloseEvent event) {}


    public byte getMenuSize() {
        return (byte) this.partedTitles.size();
    }

    public static void loadTitles() {
        titleConfig = new TitleConfig("titles");
        allTitles = titleConfig.loadTitles();
    }
}
