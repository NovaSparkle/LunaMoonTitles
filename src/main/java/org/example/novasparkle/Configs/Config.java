package org.example.novasparkle.Configs;

import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.example.novasparkle.LunaMoonTitles;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Config {
    private static FileConfiguration config;
    private static String PREFIX;
    private static String MAIN_COLOR;
    private static String SECONDARY_COLOR;
    public static void load() {
        LunaMoonTitles.getINSTANCE().saveDefaultConfig();
        config = LunaMoonTitles.getINSTANCE().getConfig();

        MAIN_COLOR = getString("messages.MAIN_COLOR");
        SECONDARY_COLOR = getString("messages.SECONDARY_COLOR");
        PREFIX = getString("messages.PREFIX")
                .replaceAll("\\{M}", MAIN_COLOR)
                .replaceAll("\\{S}", SECONDARY_COLOR);

    }
    public static void reload() {
        LunaMoonTitles.getINSTANCE().reloadConfig();
        config = LunaMoonTitles.getINSTANCE().getConfig();
        MAIN_COLOR = getString("messages.MAIN_COLOR");
        SECONDARY_COLOR = getString("messages.SECONDARY_COLOR");
        PREFIX = getString("messages.PREFIX")
                .replaceAll("\\{M}", MAIN_COLOR)
                .replaceAll("\\{S}", SECONDARY_COLOR);
    }
    public static int getInt(String path) {
        return config.getInt(path);
    }
    public static String  getString(String path) {
        return config.getString(path);
    }
    public static List<String> getStringList(String path) {
        return config.getStringList(path).stream().map(Config::color).collect(Collectors.toList());
    }
    public static String getMessage(String path) {
        return color(Objects.requireNonNull(config.getString(String.format("messages.%s", path))));
    }
    public static ConfigurationSection getSection(String path) {
        return config.getConfigurationSection(path);
    }
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text
                .replaceAll("\\{M}", MAIN_COLOR)
                .replaceAll("\\{S}", SECONDARY_COLOR)
                .replaceAll("\\{P}", PREFIX));
    }
}
