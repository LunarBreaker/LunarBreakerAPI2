package com.lunarbreaker.api.commands;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Used to show players what client they or other players are on
*/
public class ClientCommand implements CommandExecutor {

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

        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        sender.sendMessage(ChatColor.YELLOW + target.getPlayerListName() + ":");
        if(LunarBreakerAPI.getInstance().isRunningLunarClient(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.DARK_AQUA + "Lunar Client");
        }else if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.RED + "CheatBreaker");
        }else {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.WHITE + "Vanilla");
        }
        if(LunarBreakerAPI.getInstance().isOn18(target)) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Version" + ChatColor.GRAY + ": " + ChatColor.WHITE + "1.8");
        }else {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Version" + ChatColor.GRAY + ": " + ChatColor.WHITE + "1.7");
        }
        if(LunarBreakerAPI.getInstance().isRunningLunarClient(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Protected" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "Maybe");
        }else if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Protected" + ChatColor.GRAY + ": " + ChatColor.RED + "No");
        }else {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Protected" + ChatColor.GRAY + ": " + ChatColor.RED + "No");
        }
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");

        return true;
    }

}