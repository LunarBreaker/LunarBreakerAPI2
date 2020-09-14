package com.lunarbreaker.api.commands;

import com.lunarbreaker.api.LunarBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

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

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player was not found.");
                return false;
            }
        }

        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + String.join("", Collections.nCopies(53, "-")));
        sender.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY + ":");

        if(LunarBreakerAPI.getInstance().isRunningForge(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Forge");
        }else if(LunarBreakerAPI.getInstance().isRunningLunarClient(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.DARK_AQUA + "Lunar Client");
        }else if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(target.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.RED + "CheatBreaker");
        }else {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Client" + ChatColor.GRAY + ": " + ChatColor.WHITE + "Vanilla");
        }

        List<String> channels =  LunarBreakerAPI.getInstance().getChannels().get(target.getUniqueId());
        if(channels != null && !channels.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Channel" + (channels.size() == 1 ? "" : "s") + ChatColor.GRAY + ": " +
                    ChatColor.WHITE + String.join(ChatColor.GRAY + ", " + ChatColor.WHITE, channels));
        }

        List<String> brands =  LunarBreakerAPI.getInstance().getBrands().get(target.getUniqueId());
        if(brands != null && !brands.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Brand" + (brands.size() == 1 ? "" : "s") + ChatColor.GRAY + ": " +
                    ChatColor.WHITE + String.join(ChatColor.GRAY + ", " + ChatColor.WHITE, brands));
        }

        if(LunarBreakerAPI.getInstance().isOn18(target)) {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Version" + ChatColor.GRAY + ": " + ChatColor.WHITE + LunarBreakerAPI.getInstance().getLunarVersion(target)+ ChatColor.GRAY + " (1.8+)");
        }else {
            sender.sendMessage(ChatColor.GRAY + "   • " + ChatColor.AQUA + "Version" + ChatColor.GRAY + ": " + ChatColor.WHITE + LunarBreakerAPI.getInstance().getLunarVersion(target)+ ChatColor.GRAY + " (1.7)");
        }
        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + String.join("", Collections.nCopies(53, "-")));

        return true;
    }

}