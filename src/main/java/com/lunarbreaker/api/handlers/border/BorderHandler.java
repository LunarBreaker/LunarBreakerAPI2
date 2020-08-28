package com.lunarbreaker.api.handlers.border;

import com.cheatbreaker.nethandler.server.CBPacketWorldBorder;
import com.cheatbreaker.nethandler.server.CBPacketWorldBorderRemove;
import com.cheatbreaker.nethandler.server.CBPacketWorldBorderUpdate;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorder;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderUpdate;
import org.bukkit.entity.Player;

public class BorderHandler {

    private final LunarBreakerAPI plugin;

    public BorderHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player   The player to send the border to
     * @param border   The border to send
     */
    public void sendBorder(Player player, Border border) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketWorldBorder(
                    border.getId(),
                    border.getWorld(),
                    border.isCancelsExit(),
                    border.isCanShrinkExpand(),
                    border.getColor(),
                    border.getMinX(),
                    border.getMinZ(),
                    border.getMaxX(),
                    border.getMaxZ()
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketWorldBorder(
                    border.getId(),
                    border.getWorld(),
                    border.isCancelsExit(),
                    border.isCanShrinkExpand(),
                    border.getColor(),
                    border.getMinX(),
                    border.getMinZ(),
                    border.getMaxX(),
                    border.getMaxZ()
            ));
        }
    }

    /**
     * @param player   The player to send the update
     * @param id       The id of the border to update
     * @param minX     The minimum X coordinate
     * @param minZ     The minimum Z coordinate
     * @param maxX     The maximum X coordinate
     * @param maxZ     The maximum Z coordinate
     */
    public void updateBorder(Player player, String id, double minX, double minZ, double maxX, double maxZ) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketWorldBorderUpdate(
                    id,
                    minX,
                    minZ,
                    maxX,
                    maxZ,
                    0
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketWorldBorderUpdate(
                    id,
                    minX,
                    minZ,
                    maxX,
                    maxZ,
                    0
            ));
        }
    }

    /**
     * @param player   The player to remove the border from
     * @param id       The id of the border to remove
     */
    public void removeBorder(Player player, String id) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketWorldBorderRemove(id));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketWorldBorderRemove(id));
        }
    }

}
