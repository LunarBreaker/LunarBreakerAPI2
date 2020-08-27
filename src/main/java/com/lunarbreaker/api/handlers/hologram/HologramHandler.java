package com.lunarbreaker.api.handlers.hologram;

import com.cheatbreaker.nethandler.server.CBPacketAddHologram;
import com.cheatbreaker.nethandler.server.CBPacketRemoveHologram;
import com.cheatbreaker.nethandler.server.CBPacketUpdateHologram;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologram;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramUpdate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

/*
 * Used to handle all hologram packets
*/
public class HologramHandler {

    private final LunarBreakerAPI plugin;

    public HologramHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player     The player to send the hologram to
     * @param id         The unique id of the hologram
     * @param position   The location of the hologram
     * @param lines      The lines that the hologram should show
     */
    public void addHologram(Player player, UUID id, Location position, String[] lines) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketHologram(id, position.getX(), position.getY(), position.getZ(), Arrays.asList(lines)));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketAddHologram(id, position.getX(), position.getY(), position.getZ(), Arrays.asList(lines)));
        }
    }

    /**
     * @param player   The player to send the hologram to
     * @param id       The unique id of the hologram
     * @param lines    The lines that the hologram should show
     */
    public void updateHologram(Player player, UUID id, String[] lines) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketHologramUpdate(id, Arrays.asList(lines)));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketUpdateHologram(id, Arrays.asList(lines)));
        }
    }

    /**
     * @param player   The player to remove the hologram from
     * @param id       The unique id of the hologram
     */
    public void removeHologram(Player player, UUID id) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketHologramRemove(id));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketRemoveHologram(id));
        }
    }

}
