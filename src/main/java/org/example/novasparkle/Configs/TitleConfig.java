package org.example.novasparkle.Configs;

import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.novasparkle.LunaMoonTitles;
import org.example.novasparkle.Titles.TitleItem;
import sun.security.util.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TitleConfig {
    private final File file;
    private final FileConfiguration config;

    @SneakyThrows
    @SuppressWarnings("all")
    public TitleConfig(String fileName) {
        LunaMoonTitles INSTANCE = LunaMoonTitles.getINSTANCE();
        this.file = new File(INSTANCE.getDataFolder(), fileName + ".yml");
        INSTANCE.saveResource("titles.yml", false);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void write(String tag, String displayName) {
        ConfigurationSection section = this.config.getConfigurationSection("titles");
        assert section != null;
        ConfigurationSection titleSection;
        if (!section.contains(tag)) {
            titleSection = section.createSection(tag);
        } else {
            titleSection = section.getConfigurationSection(tag);
        }
        assert titleSection != null;
        titleSection.set("displayName", displayName);
        this.save();
    }
    public List<TitleItem> loadTitles() {
        List<TitleItem> list = new ArrayList<>();
        ConfigurationSection section = this.config.getConfigurationSection("titles");
        assert section != null;
        for (String key : section.getKeys(false)) {
            list.add(new TitleItem(key, section.getString(key)));
        }
        return list;
    }
    public Material getMaterial(boolean opened) {
        if (opened)
            return Material.getMaterial(Objects.requireNonNull(this.config.getString("settings.unlocked.material")));
        else
            return Material.getMaterial(Objects.requireNonNull(this.config.getString("settings.locked.material")));
    }
    public List<String> getLore(boolean opened) {
        if (opened)
            return this.config.getStringList("settings.unlocked.lore");
        else
            return this.config.getStringList("settings.locked.lore");    }
    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
