package me.thiagocodex.automessages;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

class PrintTask extends CustomConfig {
    static BukkitTask bukkitTask;
    static List<ComponentBuilder> componentBuilders = new ArrayList<>();
    static List<Sound> sounds = new ArrayList<>();

    static int count = 0;

    static void start() {
        String delay = getConfig().getString("AutoMessages.StartDelayInSeconds");
        String period = getConfig().getString("AutoMessages.DelayInSeconds");
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (componentBuilders.size() > 0) {
                        if (player.hasPermission("automessages.show." + (count + 1))) {
                            ComponentBuilder componentBuilder = new ComponentBuilder(componentBuilders.get(count));
                            player.sendMessage("");
                            player.playSound(player.getLocation(), sounds.get(count), 1, 1);
                            player.spigot().sendMessage(componentBuilder.create());
                            player.sendMessage("");
                        }
                    }
                }
                if (componentBuilders.size() > 0) {
                    count++;
                }
                if (count == componentBuilders.size()) {
                    count = 0;
                }
            }
        }.runTaskTimerAsynchronously(plugin, Integer.parseInt(delay) * 20, Integer.parseInt(period) * 20);
    }
}