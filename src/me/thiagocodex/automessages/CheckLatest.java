package me.thiagocodex.automessages;

public class CheckLatest extends CustomConfig {


    public static String message;

    public static void writeInConsole(){

        plugin.getServer().getConsoleSender().sendMessage(message);


    }

}
