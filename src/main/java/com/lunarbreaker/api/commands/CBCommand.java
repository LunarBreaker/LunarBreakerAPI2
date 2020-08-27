package com.lunarbreaker.api.commands;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Used to show players if they are/another player is on CB
 */
public class CBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players.");
            return false;
        }

        Player target = (Player) sender;

        if(args.length > 0) {
            target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                sender.sendMessage(ChatColor.RED + "That player was not found.");
                return false;
            }
        }

        if(!LunarBreakerAPI.getInstance().isRunningCheatBreaker(target.getUniqueId())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + " &cis NOT on CheatBreaker."));
        }else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + target.getName() + " &ais currently on CheatBreaker."));
        }

        return true;
    }

}