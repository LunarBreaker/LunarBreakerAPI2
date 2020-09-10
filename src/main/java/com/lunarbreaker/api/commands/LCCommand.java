package com.lunarbreaker.api.commands;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Used to show players if they are/another player is on LC
*/
public class LCCommand implements CommandExecutor {

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

        if(!LunarBreakerAPI.getInstance().isRunningLunarClient(target.getUniqueId())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getDisplayName() + " &cis NOT on Lunar Client."));
        }else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + target.getDisplayName() + " &ais currently on " + (LunarBreakerAPI.getInstance().isOn18(target) ? "1.8" : "1.7") + " Lunar Client."));
        }

        return true;
    }
}
