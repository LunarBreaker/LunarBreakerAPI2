package com.lunarbreaker.api.handlers.cooldown;

import com.cheatbreaker.nethandler.server.CBPacketCooldown;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketCooldown;
import org.bukkit.entity.Player;

/*
 * Used to handle all cooldown packets
*/
public class CooldownHandler {

    private final LunarBreakerAPI plugin;

    public CooldownHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player     The player to send the cooldown to
     * @param cooldown   The cooldown to send
     */
    public void sendCooldown(Player player, Cooldown cooldown) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketCooldown(cooldown.getMessage(), cooldown.getDurationMs(), cooldown.getIcon().getId()));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketCooldown(cooldown.getMessage(), cooldown.getDurationMs(), cooldown.getIcon().getId()));
        }
    }

    /**
     * @param player     The player to remove the cooldown from
     * @param cooldown   The cooldown to remove
     */
    public void removeCooldown(Player player, Cooldown cooldown) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketCooldown(cooldown.getMessage(), 0L, cooldown.getIcon().getId()));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketCooldown(cooldown.getMessage(), 0L, cooldown.getIcon().getId()));
        }
    }

}
