package com.lunarbreaker.api.handlers.bossbar;

import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketBossBar;
import org.bukkit.entity.Player;

/*
 * Used to handle all bossbar packets
*/
public class BossbarHandler {

    private final LunarBreakerAPI plugin;

    public BossbarHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player   The player to send the bossbar to
     * @param text     The message the bossbar contains
     * @param health   The health of the bossbar
     */
    public void setBossbar(Player player, String text, float health) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketBossBar(0, text, health));
        }
    }

    /**
     * @param player   The player to remove the bossbar from
     */
    public void unsetBossbar(Player player) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketBossBar(1, null, 0.0f));
        }
    }

}
