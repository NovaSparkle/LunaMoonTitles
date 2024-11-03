package org.example.novasparkle.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.example.novasparkle.Configs.Config;
import org.example.novasparkle.Menus.InventoryManager;
import org.example.novasparkle.Menus.Menu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.print.DocFlavor;
import java.util.Collections;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    Menu menu = new Menu(player, (byte) 1);
                    InventoryManager.openInventory(player, menu);
                    break;
                case 1:
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (!player.hasPermission("LMTitles.reload")) {
                            player.sendMessage(Config.getMessage("noPermission"));
                            return true;
                        } else {
                            Config.reload();
                            Menu.loadTitles();
                            player.sendMessage(Config.getMessage("configReloaded"));
                        }

                    }
                    break;
            }
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return null;
    }
}
