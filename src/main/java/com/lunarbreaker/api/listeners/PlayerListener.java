package com.lunarbreaker.api.listeners;

import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarbreaker.api.client.Client;
import com.lunarbreaker.api.events.PlayerRegisterEvent;
import com.lunarbreaker.api.events.PlayerUnregisterEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

/*
 * Used to handle all registrations of CB/LC
*/
public class PlayerListener implements Listener {
    
    private final LunarBreakerAPI plugin;
    
    public PlayerListener(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRegister(PlayerRegisterChannelEvent event) {
        if(event.getChannel().equals(LunarBreakerAPI.getCB_MESSAGE_CHANNEL())) {
            plugin.getPlayers().put(event.getPlayer().getUniqueId(), Client.CB);

            plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(event.getPlayer(), Client.CB));
            plugin.getWorldHandler().updateWorld(event.getPlayer());
        }else if(event.getChannel().equals(LunarBreakerAPI.getLC_MESSAGE_CHANNEL())) {
            plugin.getPlayers().put(event.getPlayer().getUniqueId(), Client.LC);

            plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(event.getPlayer(), Client.LC));
            plugin.getWorldHandler().updateWorld(event.getPlayer());
        }
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        if (event.getChannel().equals(LunarBreakerAPI.getCB_MESSAGE_CHANNEL()) || event.getChannel().equals(LunarBreakerAPI.getLC_MESSAGE_CHANNEL())) {
            plugin.getPlayers().remove(event.getPlayer().getUniqueId());

            plugin.getServer().getPluginManager().callEvent(new PlayerUnregisterEvent(event.getPlayer(), event.getChannel().equals(LunarBreakerAPI.getCB_MESSAGE_CHANNEL())? Client.CB : Client.LC));
        }
    }

    @EventHandler
    public void onUnregister(PlayerQuitEvent event) {
        plugin.getPlayers().remove(event.getPlayer().getUniqueId());
    }

}
