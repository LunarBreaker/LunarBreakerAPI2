package com.lunarbreaker.api.handlers.ghost;

import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketGhost;
import org.bukkit.entity.Player;

/*
 * Used to handle all ghost packets
*/
public class GhostHandler {

    private final LunarBreakerAPI plugin;

    public GhostHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player   The player to send the ghosts to
     * @param ghost    The ghosts to send/unsend
     */
    public void sendGhost(Player player, Ghost ghost) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketGhost(ghost.getGhostedPlayers(), ghost.getUnGhostedPlayers()));
        }
    }

}
