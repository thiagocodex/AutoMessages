package me.thiagocodex.automessages;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class News extends CustomConfig implements Listener {
    private static HttpURLConnection httpURLConnection;
    private static String stringVersion;
    private final AutoMessages autoMessages = AutoMessages.getPlugin(AutoMessages.class);

    static String showNews() {
        try {
            StringBuilder news = new StringBuilder();
            URL url = new URL("https://raw.githubusercontent.com/thiagocodex/AutoMessages/main/ANNOUNCE");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            boolean isShow = in.readLine().equalsIgnoreCase("Enabled: true");
            String inputLine;
            while ((isShow && (inputLine = in.readLine()) != null)) news.append(inputLine.replace("Enabled: true", ""));
            in.close();
            return news.toString();
        } catch (IOException e) {
            return "Don't worry, can't connect to db!";
        }
    }

    static float getVersion() {
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
            stringVersion = stringBuilder.toString().replaceAll("%", "");
            if (Integer.parseInt(plugin.getDescription().getVersion()
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < version) {
                CheckLatest.message = ChatColor.RED + " You don't have the latest version.";
            } else {
                CheckLatest.message = ChatColor.GREEN + "    You have the latest version!   ";
            }
            return version;
        } catch (IOException e) {
            CheckLatest.message = ChatColor.YELLOW + " Don't worry, can't connect to db!";
        } finally {
            httpURLConnection.disconnect();
        }
        return 0;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (config.getBoolean("ShowNews")) {
            ComponentBuilder componentBuilder = new ComponentBuilder("");
            TextComponent textComponent = new TextComponent();
            textComponent.setText("\n" + autoMessages.prefix + " " + color(showNews()) + "\n");
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("https://github.com/thiagocodex/Discord_Bot").create());
            textComponent.setHoverEvent(hoverEvent);
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/thiagocodex/Discord_Bot");
            textComponent.setClickEvent(clickEvent);
            componentBuilder.append(textComponent);
            event.getPlayer().spigot().sendMessage(componentBuilder.create());
        }
        if (event.getPlayer().isOp()) {
            if (Integer.parseInt(plugin.getDescription().getVersion()
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < getVersion()) {
                ComponentBuilder componentBuilder = new ComponentBuilder("");
                TextComponent textComponent = new TextComponent();
                textComponent.setText(
                        "\n" + autoMessages.prefix + " §cThere is a new version available\n" +
                                "§eCurrent: " + plugin.getDescription().getVersion() + "\n" +
                                "§aAvailable: " + stringVersion + "\n" +
                                "§cClick §bhere §cto download\n");
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("https://www.spigotmc.org/resources/automessages.85483/").create());
                textComponent.setHoverEvent(hoverEvent);
                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/automessages.85483/");
                textComponent.setClickEvent(clickEvent);
                componentBuilder.append(textComponent);
                event.getPlayer().spigot().sendMessage(componentBuilder.create());
            }
        }
    }
}
