package me.thiagocodex.automessages;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class News extends CustomConfig implements Listener {
    private static HttpURLConnection httpURLConnection;
    private static String stringVersion;
    private static String isAnnounceSession;

    BufferedReader in(String url) {
        try {
            BufferedReader br;
            URL url1 = new URL(url);


            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            return br;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    float getVersion() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            URL url = new URL("https://raw.githubusercontent.com/thiagocodex/AutoMessages/main/VERSION");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) stringBuilder.append(inputLine);
            in.close();
            int version = Integer.parseInt(stringBuilder.toString().replaceAll("%", "").replaceAll("\\.", ""));
            stringVersion = stringBuilder.toString().replaceAll("%", ""); //1.0.1
            if (Integer.parseInt(plugin.getDescription().getVersion() //100
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < version) {
                CheckLatest.message = ChatColor.YELLOW + "[AutoMessages]" + ChatColor.RED + " You don't have the latest version.";
            } else {
                CheckLatest.message = ChatColor.YELLOW + "[AutoMessages]" + ChatColor.GREEN + " You have the latest version.";
            }
            return version;
        } catch (IOException e) {
            stringVersion = " §eDon't worry, can't connect to db!§r";
        } finally {
            httpURLConnection.disconnect();
        }
        return 0;




    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            if (Integer.parseInt(plugin.getDescription().getVersion()
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < getVersion()) {
                event.getPlayer().sendMessage(
                        CheckLatest.message + "\n" + ChatColor.WHITE + "Current: " + ChatColor.YELLOW +  plugin.getDescription().getVersion() + "\n" + ChatColor.WHITE +"Latest: " + ChatColor.GREEN + stringVersion + "\nhttps://www.spigotmc.org/resources/automessages.85483/");
            }
        }
    }
}
