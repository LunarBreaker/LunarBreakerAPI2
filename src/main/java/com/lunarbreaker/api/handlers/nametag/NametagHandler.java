package com.lunarbreaker.api.handlers.nametag;

import com.cheatbreaker.nethandler.server.CBPacketOverrideNametags;
import com.google.common.collect.ImmutableList;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsOverride;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Used to handle all nametag packets
*/
public class NametagHandler {

    private final LunarBreakerAPI plugin;

    public NametagHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param viewer    The player the nametag should update for
     * @param nametag   The nametag that should show (supports multiple lines)
     * @param target    The player the nametag belongs to
     */
    public void overrideNametag(Player viewer, Collection<String> nametag, Player target) {
        if(plugin.isRunningLunarClient(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), new ArrayList<>(nametag)));
        } else if(plugin.isRunningCheatBreaker(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new CBPacketOverrideNametags(target.getUniqueId(), new ArrayList<>(nametag)));
        }
    }

    /**
     * @param viewer   The player the nametag should update for
     * @param target   The player the nametag belongs to
     */
    public void resetNametag(Player viewer, Player target) {
        if(plugin.isRunningLunarClient(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), null));
        }else if(plugin.isRunningCheatBreaker(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new CBPacketOverrideNametags(target.getUniqueId(), null));
        }
    }

    /**
     * @param viewer   The player the nametag should hide for
     * @param target   The player the nametag belongs to
     */
    public void hideNametag(Player viewer, Player target) {
        if(plugin.isRunningLunarClient(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), ImmutableList.of()));
        }else if(plugin.isRunningCheatBreaker(viewer.getUniqueId())) {
            plugin.sendPacket(viewer, new CBPacketOverrideNametags(target.getUniqueId(), ImmutableList.of()));
        }
    }

}
