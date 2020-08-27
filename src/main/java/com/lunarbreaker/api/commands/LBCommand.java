package com.lunarbreaker.api.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/*
 * Used for credits towards all contributors
*/
public class LBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        sender.sendMessage(ChatColor.YELLOW + "This server is running " + ChatColor.RED + "LunarBreakerAPI");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "Contributors:");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "FrozenOrb " + ChatColor.GRAY + "(CheatBreaker NetHandler)");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.DARK_AQUA + "Moonsworth " + ChatColor.GRAY + "(Lunar Client NetHandler)");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.GREEN + "ProPractice " + ChatColor.GRAY + "(LunarBreakerAPI)");
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");

        return true;
    }

}
