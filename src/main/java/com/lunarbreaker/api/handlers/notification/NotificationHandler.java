package com.lunarbreaker.api.handlers.notification;

import com.cheatbreaker.nethandler.server.CBPacketNotification;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNotification;
import org.bukkit.entity.Player;

/*
 * Used to handle all notification packets
*/
public class NotificationHandler {

    private final LunarBreakerAPI plugin;

    public NotificationHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player         The player the notification should send to
     * @param notification   The notification to send
     */
    public void sendNotification(Player player, Notification notification) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketNotification(
                    notification.getMessage(),
                    notification.getDurationMs(),
                    notification.getLevel().name()
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketNotification(
                    notification.getMessage(),
                    notification.getDurationMs(),
                    notification.getLevel().name()
            ));
        }
    }

    /**
     * @param player         The player the notification should send to
     * @param notification   The notification to send
     * @param fallback       The fallback runnable if the user is not on LC or CB
     */
    public void sendNotificationOrFallback(Player player, Notification notification, Runnable fallback) {
        if (plugin.isRunningCheatBreaker(player.getUniqueId()) || (plugin.isRunningLunarClient(player.getUniqueId()) && !plugin.isOn18(player)) ) {
            sendNotification(player, notification);
        } else {
            fallback.run();
        }
    }

}
