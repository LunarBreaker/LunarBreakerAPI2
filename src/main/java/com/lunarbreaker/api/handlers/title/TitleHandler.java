package com.lunarbreaker.api.handlers.title;

import com.cheatbreaker.nethandler.server.CBPacketTitle;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle;
import org.bukkit.entity.Player;

import java.time.Duration;

/*
 * Used to handle all title packets
*/
public class TitleHandler {

    private final LunarBreakerAPI plugin;

    public TitleHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player        The player to send the title to
     * @param type          The type of title to send
     * @param message       The message the title contains
     * @param fadeInTime    The time it takes to fade in
     * @param displayTime   The time the title is shown for
     * @param fadeOutTime   The time it takes to fade out
     * @param scale         The scale of the title
     */
    public void sendTitle(Player player, TitleType type, String message, Duration fadeInTime, Duration displayTime, Duration fadeOutTime, float scale) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketTitle(type.name().toLowerCase(), message, scale, displayTime.toMillis(), fadeInTime.toMillis(), fadeOutTime.toMillis()));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketTitle(type.name().toLowerCase(), message, scale, displayTime.toMillis(), fadeInTime.toMillis(), fadeOutTime.toMillis()));
        }
    }

}
