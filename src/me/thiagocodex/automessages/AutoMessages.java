package me.thiagocodex.automessages;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AutoMessages extends JavaPlugin {
    String prefix;
    String enabled;
    String reload;

    @Override
    public void onEnable() {
        new FutureRequest().getNewsMessage();
        getServer().getPluginManager().registerEvents(new News(), this);
        getCommand("am").setExecutor(new Commands());
        try {
            CustomConfig.createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomConfig.load();
        load();
        getServer().getConsoleSender().sendMessage(prefix + " " + enabled);

        getServer().getConsoleSender().sendMessage(CustomConfig.color(
                "\n\n" +
                        "+-----------------------------------------------+\n" +
                        "|      " + prefix + "§a by: thiagocodex#2280      §r|\n" +
                        "+-----------------------------------------------+\n" +
                        "|          §7STATUS:            §aENABLED§r           |\n" +
                        "+-----------------------------------------------+\n" +
                        "|      " + News.versionMessage + "      §r|\n" +
                        "+-----------------------------------------------+\n"));
        PrintTask.start();
        TextFormat.get();
    }

    void load() {
        try {
            CustomConfig.createFiles();
            CustomConfig.reloadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
