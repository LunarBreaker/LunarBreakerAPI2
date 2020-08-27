package com.lunarbreaker.api.handlers.waypoint;

import com.cheatbreaker.nethandler.shared.CBPacketAddWaypoint;
import com.cheatbreaker.nethandler.shared.CBPacketRemoveWaypoint;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import org.bukkit.entity.Player;

/*
 * Used to handle all waypoint packets
*/
public class WaypointHandler {

    private final LunarBreakerAPI plugin;

    public WaypointHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player     The player to send the waypoint to
     * @param waypoint   The waypoint to send
     */
    public void sendWaypoint(Player player, Waypoint waypoint) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketWaypointAdd(
                    waypoint.getName(),
                    waypoint.getWorld(),
                    waypoint.getColor(),
                    waypoint.getX(),
                    waypoint.getY(),
                    waypoint.getZ(),
                    waypoint.isForced(),
                    waypoint.isVisible()
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketAddWaypoint(
                    waypoint.getName(),
                    waypoint.getWorld(),
                    waypoint.getColor(),
                    waypoint.getX(),
                    waypoint.getY(),
                    waypoint.getZ(),
                    waypoint.isForced(),
                    waypoint.isVisible()
            ));
        }
    }

    /**
     * @param player     The player to remove the waypoint from
     * @param waypoint   The waypoint to remove
     */
    public void removeWaypoint(Player player, Waypoint waypoint) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketWaypointRemove(
                    waypoint.getName(),
                    waypoint.getWorld()
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketRemoveWaypoint(
                    waypoint.getName(),
                    waypoint.getWorld()
            ));
        }
    }

}
