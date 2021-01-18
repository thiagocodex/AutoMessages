package me.thiagocodex.automessages;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class AutoMessages extends JavaPlugin {
    @Override
    public void onEnable() {
        new News().getVersion();
        getServer().getPluginManager().registerEvents(new News(), this);
        getCommand("am").setExecutor(new Commands());
        try {
            CustomConfig.createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomConfig.load();
        getServer().getConsoleSender().sendMessage(CustomConfig.color(
                "\n\n" +
                        "+-----------------------------------------------+\n" +
                        "|       §aAutoMessages by: thiagocodex#2280       §r|\n" +
                        "+-----------------------------------------------+\n" +
                        "|          §7STATUS:            §a§nENABLED§r           |\n" +
                        "+-----------------------------------------------+\n" +
                        "|      " + CheckLatest.message + "       §r|\n" +
                        "+-----------------------------------------------+\n"));
        PrintTask.start();
        Text.get();
    }
}
