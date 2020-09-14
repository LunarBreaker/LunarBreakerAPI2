package com.lunarbreaker.api.commands;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;

/*
 * Used for credits towards all contributors
*/
public class LBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + String.join("", Collections.nCopies(53, "-")));
        sender.sendMessage(ChatColor.YELLOW + "This server is running " + ChatColor.RED + "LunarBreakerAPI2" + ChatColor.GRAY + " (" + LunarBreakerAPI.getInstance().getDescription().getVersion() + ")");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "Contributors:");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "FrozenOrb " + ChatColor.GRAY + "(CheatBreaker NetHandler)");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.DARK_AQUA + "Moonsworth " + ChatColor.GRAY + "(Lunar Client NetHandler)");
        sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.GREEN + "ProPractice " + ChatColor.GRAY + "(LunarBreakerAPI2)");
        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + String.join("", Collections.nCopies(53, "-")));

        return true;
    }

}
