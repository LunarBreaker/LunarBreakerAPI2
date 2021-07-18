package com.lunarbreaker.api.utils;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class SafeCommandRegister {

    public static void registerCommand(JavaPlugin plugin, String name, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(name);
        if(command != null) {
            command.setExecutor(executor);
        } else {
            Bukkit.getLogger().log(Level.WARNING, "Command '" + name + "' is not defined in the plugin.yml");
        }
    }

}
