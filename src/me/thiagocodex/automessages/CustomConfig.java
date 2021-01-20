package me.thiagocodex.automessages;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

abstract class CustomConfig {
    public static void reloadConfig() {
        plugin.prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("AutoMessages.Prefix"));
        plugin.enabled = ChatColor.translateAlternateColorCodes('&', getConfig().getString("AutoMessages.Enabled"));
        plugin.reload = ChatColor.translateAlternateColorCodes('&', getConfig().getString("AutoMessages.Reloaded"));
    }

    static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    static final AutoMessages plugin = AutoMessages.getPlugin(AutoMessages.class);
    static File configFile = new File(plugin.getDataFolder(), "config.yml");
    static FileConfiguration config;

    static void createFiles() throws IOException {
        if (Files.notExists(plugin.getDataFolder().toPath())) {
            Files.createDirectory(plugin.getDataFolder().toPath());
        }
        if (Files.notExists(configFile.toPath())) {
            Files.createFile(configFile.toPath());
            writeContent();
        }
    }

    static FileConfiguration getConfig() {
        return config;
    }

    static void load() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    static void writeContent() {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8));
            writer.write("AutoMessages:\n" +
                    "#Plugin prefix for messages\n" +
                    "  Prefix: '&f[&2Auto&bMessages&f]'\n" +
                    "\n" +
                    "#Plugin enabled message to show at server starts\n" +
                    "  Enabled: '&aEnabled successfully'\n" +
                    "\n" +
                    "#The message that will appear on plugin reloads\n" +
                    "  Reloaded : '&aReloaded successfully'\n" +
                    "\n" +
                    "#Time in seconds until the first message is triggered after the plugin starts or after reloading\n" +
                    "  StartDelayInSeconds: 10\n" +
                    "\n" +
                    "#Delay time between messages to show in chat\n" +
                    "  DelayInSeconds: 10\n" +
                    "\n" +
                    "#------------------------------------------------------------------------------#\n" +
                    "#Message index; 1 means the first to be displayed\n" +
                    "  '1':\n" +
                    "\n" +
                    "#&h means has hover\n" +
                    "#&u means has url\n" +
                    "    Text: '&f&lFollow &f&lme &f&lon &f&lmy &f&lsocial &f&lnetworks\\n &h&u&5&lTwitch\\n &h&u&f&lYou&4&lTube\\n &h&u&b&lFacebook\\n &h&u&9&lDiscord'\n" +
                    "\n" +
                    "#1st Hovered text by the 1st \"&h\" word occurrence\n" +
                    "    Hover1: '&5&lthiagocodex'\n" +
                    "\n" +
                    "#2nd Hovered text by the 2nd \"&h\" word occurrence\n" +
                    "    Hover2: '&f&lthiago&4&lcodex'\n" +
                    "    \n" +
                    "    Hover3: '&b&lTheCoders'\n" +
                    "    Hover4: '&3&lThe Coders™ - 128'\n" +
                    "\n" +
                    "#1st URL link by the 1st \"&u\" word occurrence\n" +
                    "    URL1: https://www.twitch.tv/thiagocodex\n" +
                    "    URL2: https://www.youtube.com/channel/UCEDjQf5cEkH4320GevAitUA?sub_confirmation=1\n" +
                    "    URL3: https://www.facebook.com/thecoders/\n" +
                    "    URL4: https://discord.gg/3HTqPFDBmT\n" +
                    "    \n" +
                    "#Sound to play when message appear\n" +
                    "    Sound: UI_TOAST_IN #https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html\n" +
                    "\n" +
                    "#------------------------------------------------------------------------------#\n" +
                    "  '2':\n" +
                    "    Text: '&f&lFollow &f&lme &f&lon &c&lGit&b&lHub&f&l: &h&u&8&lthiagocodex &f&lwatch &f&lautomessages &h&u&a&lrepository'\n" +
                    "    Hover1: '&f&o&lPlaying Minecraft'\n" +
                    "    Hover2: '&f&o&lAutomated scheduler for eventable and clickable server messages'\n" +
                    "    URL1: https://github.com/thiagocodex\n" +
                    "    URL2: https://github.com/thiagocodex/AutoMessages\n" +
                    "    Sound: UI_TOAST_IN\n" +
                    "\n" +
                    "#------------------------------------------------------------------------------#\n" +
                    "  '3':\n" +
                    "#&p means has player command\n" +
                    "    Text: '&c&l&h&pPlugins'\n" +
                    "    Hover1: '&e&lClick to show server plugins'\n" +
                    "    Command1: /pl\n" +
                    "    Sound: UI_TOAST_IN\n" +
                    "    \n" +
                    "#------------------------------------------------------------------------------#\n" +
                    "\n" +
                    "#If set as true, if available, you'll allow me to send news message to players when they're joining to your server, I appreciate that\n" +
                    "ShowNews: true");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
