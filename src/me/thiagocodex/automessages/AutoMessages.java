package me.thiagocodex.automessages;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AutoMessages extends JavaPlugin {
    String prefix;
    String enabled;
    String reload;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new News(), this);
        getCommand("am").setExecutor(new Commands());
        try {
            CustomConfig.createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomConfig.load();
        load();
        if (getConfig().getBoolean("ShowNews")) {
            News.showNews();
        }
        News.getVersion();
        getServer().getConsoleSender().sendMessage(prefix + " " + enabled);
        getServer().getConsoleSender().sendMessage(CustomConfig.color(
                "\n\n" +
                        "+-----------------------------------------------+\n" +
                        "|      " + prefix + "§a by: thiagocodex#2280      §r|\n" +
                        "+-----------------------------------------------+\n" +
                        "|          §7STATUS:            §a§nENABLED§r           |\n" +
                        "+-----------------------------------------------+\n" +
                        "|      " + CheckLatest.message + "      §r|\n" +
                        "+-----------------------------------------------+\n"));
        PrintTask.start();
        Text.get();
    }

    public void load() {
        try {
            CustomConfig.createFiles();
            CustomConfig.reloadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
