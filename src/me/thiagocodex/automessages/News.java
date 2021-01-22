package me.thiagocodex.automessages;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class News extends CustomConfig implements Listener {

    static final AutoMessages autoMessages = AutoMessages.getPlugin(AutoMessages.class);
    static HttpURLConnection httpURLConnection;
    static String stringVersion;
    static String versionMessage;
    static int version;
    static boolean isShow;
    static String newsMessage;

    static void request() {
        final StringBuilder sbVersion = new StringBuilder();
        final StringBuilder sbNewsMessage = new StringBuilder();
        try {
            URL versionUrl = new URL("https://raw.githubusercontent.com/thiagocodex/AutoMessages/main/VERSION");
            URL announceUrl = new URL("https://raw.githubusercontent.com/thiagocodex/AutoMessages/main/ANNOUNCE");
            httpURLConnection = (HttpURLConnection) versionUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            BufferedReader versionInBr = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            httpURLConnection = (HttpURLConnection) announceUrl.openConnection();
            BufferedReader announceInBr = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            isShow = announceInBr.readLine().equalsIgnoreCase("Enabled: true");
            String versionInputLine;
            String announceInputLine;
            while ((versionInputLine = versionInBr.readLine()) != null) sbVersion.append(versionInputLine);
            versionInBr.close();
            while ((announceInputLine = announceInBr.readLine()) != null)
                sbNewsMessage.append(announceInputLine.replace("Enabled: true", ""));
            announceInBr.close();
            stringVersion = sbVersion.toString().replaceAll("%", "");
            version = Integer.parseInt(sbVersion.toString().replaceAll("%", "").replaceAll("\\.", ""));
            newsMessage = sbNewsMessage.toString();
            if (Integer.parseInt(plugin.getDescription().getVersion()
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < version) {
                versionMessage = ChatColor.RED + " You don't have the latest version.";
            } else {
                versionMessage = ChatColor.GREEN + "    You have the latest version!   ";
            }
        } catch (IOException e) {
            version = 0;
            versionMessage = "Don't worry, can't connect to db!";
        } finally {
            httpURLConnection.disconnect();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (config.getBoolean("ShowNews") && isShow) {
            ComponentBuilder componentBuilder = new ComponentBuilder("");
            TextComponent textComponent = new TextComponent();
            textComponent.setText("\n" + autoMessages.prefix + " " + color(newsMessage) + "\n");
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("https://github.com/thiagocodex/Discord_Bot"));
            textComponent.setHoverEvent(hoverEvent);
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/thiagocodex/Discord_Bot");
            textComponent.setClickEvent(clickEvent);
            componentBuilder.append(textComponent);
            event.getPlayer().spigot().sendMessage(componentBuilder.create());
        }
        if (event.getPlayer().isOp()) {
            if (Integer.parseInt(plugin.getDescription().getVersion()
                    .replaceAll("%", "")
                    .replaceAll("\\.", "")) < version) {
                ComponentBuilder componentBuilder = new ComponentBuilder("");
                TextComponent textComponent = new TextComponent();
                textComponent.setText(
                        "\n" + autoMessages.prefix + " §cThere is a new version available\n" +
                                "§eCurrent: " + plugin.getDescription().getVersion() + "\n" +
                                "§aAvailable: " + stringVersion + "\n" +
                                "§cClick §bhere §cto download\n");
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("https://www.spigotmc.org/resources/automessages.85483/"));
                textComponent.setHoverEvent(hoverEvent);
                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/automessages.85483/");
                textComponent.setClickEvent(clickEvent);
                componentBuilder.append(textComponent);
                event.getPlayer().spigot().sendMessage(componentBuilder.create());
            }
        }
    }
}
