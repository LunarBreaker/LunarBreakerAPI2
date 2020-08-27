package com.lunarbreaker.api.handlers.server;

import com.cheatbreaker.nethandler.server.CBPacketServerRule;
import com.cheatbreaker.nethandler.server.CBPacketServerUpdate;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerRule;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerUpdate;
import org.bukkit.entity.Player;

/*
 * Used to handle all server packets
*/
public class ServerHandler {

    private final LunarBreakerAPI plugin;

    public ServerHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player       The player to send the server rule to
     * @param serverRule   The CB server rule to update
     * @param state        The state of the CB server rule
     */
    public void changeCheatBreakerServerRule(Player player, com.cheatbreaker.nethandler.obj.ServerRule serverRule, boolean state) {
        if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerRule(serverRule, state));
        }
    }

    /**
     * @param player       The player to send the server rule to
     * @param serverRule   The LC server rule to update
     * @param state        The state of the LC server rule
     */
    public void changeLunarClientServerRule(Player player, com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule serverRule, boolean state) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(serverRule, state));
        }
    }

    /**
     * @param player   The player to send the minimap status to
     * @param status   The mini map status
     */
    public void setMinimapStatus(Player player, MinimapStatus status) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.MINIMAP_STATUS, status.name()));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerRule(com.cheatbreaker.nethandler.obj.ServerRule.MINIMAP_STATUS, status.name()));
        }
    }

    /**
     * @param player   The player to update the name for
     * @param name     The name of the server to send
     */
    public void setServerName(Player player, String name) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerUpdate(name));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerUpdate(name));
        }
    }

    /**
     * @param player          The player to update the status for
     * @param isCompetitive   The competitive status
     */
    public void setCompetitiveGame(Player player, boolean isCompetitive) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.COMPETITIVE_GAME, isCompetitive));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerRule(com.cheatbreaker.nethandler.obj.ServerRule.COMPETITIVE_GAMEMODE, isCompetitive));
        }
    }

    /**
     * @param player      The player to update the handling for
     * @param isEnabled   The waypoint handling status
     */
    public void setServerHandlesWaypoints(Player player, boolean isEnabled) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.SERVER_HANDLES_WAYPOINTS, isEnabled));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerRule(com.cheatbreaker.nethandler.obj.ServerRule.SERVER_HANDLES_WAYPOINTS, isEnabled));
        }
    }

    /**
     * @param player     The player to update the enchanting for
     * @param isLegacy   The legacy enchanting status
     */
    public void setLegacyEnchanting(Player player, boolean isLegacy) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.LEGACY_ENCHANTING, isLegacy));
        }
    }

    /**
     * @param player       The player to update the shaders for
     * @param isEnabled    The shaders status
     */
    public void setShaders(Player player, boolean isEnabled) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.SHADERS_DISABLED, !isEnabled));
        }
    }

}
