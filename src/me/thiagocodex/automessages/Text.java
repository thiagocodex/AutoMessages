package me.thiagocodex.automessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;

class Text extends CustomConfig {
    static void get() {
        String keys = getConfig().getKeys(true).toString();
        String[] subsKeys = keys.replaceAll("AutoMessages\\.", "").replaceAll("[]\\[]", "").split(", ");
        List<Integer> onlyNumbers = new ArrayList<>();
        PrintTask.componentBuilders.clear();
        PrintTask.sounds.clear();
        for (String number : subsKeys) {
            if (number.matches("[0-9]{1,2}")) {
                onlyNumbers.add(Integer.parseInt(number));
            }
        }
        for (int i = 0; i < Collections.max(onlyNumbers); i++) {
            String msg = getConfig().getString("AutoMessages." + (i + 1) + ".Text");
            String sound = getConfig().getString("AutoMessages." + (i + 1) + ".Sound");
            String[] simpleWord = msg.split(" ");
            int hoverIndex = 1;
            int urlIndex = 1;
            List<TextComponent> textComponents = new ArrayList<>();
            for (String word : simpleWord) {
                TextComponent textComponent;
                if (word.contains("&h") || word.contains("&u") || word.contains("&p")) {
                    textComponent = new TextComponent();
                    textComponent.setText(color(word).replaceAll("&h", "").replaceAll("&u", "").replaceAll("&p", ""));
                    if (word.contains("&h")) {
                        String hover = getConfig().getString("AutoMessages." + onlyNumbers.get(i) + ".Hover" + hoverIndex);
                        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hover)).create()));
                        hoverIndex++;
                    }
                    if (word.contains("&u")) {
                        String url = getConfig().getString("AutoMessages." + onlyNumbers.get(i) + ".URL" + urlIndex);
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                        urlIndex++;
                    } else if (word.contains("&p")) {
                        String command = getConfig().getString("AutoMessages." + onlyNumbers.get(i) + ".Command" + urlIndex);
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
                        urlIndex++;
                    }
                } else {
                    textComponent = new TextComponent(color(word));
                }
                textComponents.add(textComponent);
                if (textComponent.getText().contains("\\n")) {
                    textComponent.setText(textComponent.getText().replaceAll("\\\\n", ""));
                    textComponents.add(new TextComponent("\n"));
                }
            }
            ComponentBuilder componentBuilder = new ComponentBuilder("");
            for (TextComponent textComponent2 : textComponents) {
                componentBuilder.reset().append(textComponent2).append(" ");
            }
            if (sound == null) {
                PrintTask.sounds.add(null);
            } else {
                PrintTask.sounds.add(Sound.valueOf(sound));
            }
            PrintTask.componentBuilders.add(componentBuilder);
        }
    }
}
