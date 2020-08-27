package com.lunarbreaker.api.handlers.emote;

import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketEmoteBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*
 * Used to handle all emote packets
*/
public class EmoteHandler {

    private final LunarBreakerAPI plugin;

    public EmoteHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player    The player who is performing the emote
     * @param emoteId   The id of the emote (https://github.com/TewPingz/LunarClientAPI/blob/master/API/src/main/java/net/mineaus/lunar/api/type/Emote.java)
     */
    public void performEmote(Player player, int emoteId) {
        Bukkit.getOnlinePlayers().forEach(other -> {
            if(plugin.isRunningLunarClient(player.getUniqueId())) {
                plugin.sendPacket(other, new LCPacketEmoteBroadcast(player.getUniqueId(), emoteId));
            }
        });
    }

}
