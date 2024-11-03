package org.example.novasparkle;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.novasparkle.Commands.Command;
import org.example.novasparkle.Configs.Config;
import org.example.novasparkle.Menus.Events;
import org.example.novasparkle.Menus.Menu;

public final class LunaMoonTitles extends JavaPlugin {
    @Getter
    private static LunaMoonTitles INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        Config.load();
        Menu.loadTitles();
        Command command = new Command();
        this.getCommand("titles").setExecutor(command);
        this.getCommand("titles").setTabCompleter(command);
        this.getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {

    }
}
