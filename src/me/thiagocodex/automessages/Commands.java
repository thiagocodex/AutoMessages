package me.thiagocodex.automessages;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            if (s.equalsIgnoreCase("am")) {
                if (commandSender.hasPermission("automessages.reload")) {
                    if (strings[0].equalsIgnoreCase("reload")) {
                        CustomConfig.load();
                        CustomConfig.plugin.load();
                        PrintTask.bukkitTask.cancel();
                        PrintTask.start();
                        Text.get();
                        commandSender.sendMessage(CustomConfig.plugin.prefix + " " + CustomConfig.plugin.reload);
                    }
                } else {
                    commandSender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                }
            }
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + "Usage: /am reload");
        }
        return true;
    }
}
