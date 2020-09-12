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

import java.util.AbstractMap;
import java.util.UUID;

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
            boolean verified = LunarBreakerAPI.getInstance().getBrands().get(event.getPlayer().getUniqueId()).contains("vanilla");
            plugin.getPlayers().put(event.getPlayer().getUniqueId(), new AbstractMap.SimpleEntry<>(Client.CB, verified));

            if(verified) plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(event.getPlayer(), Client.CB));
            plugin.getWorldHandler().updateWorld(event.getPlayer());
        }else if(event.getChannel().equals(LunarBreakerAPI.getLC_MESSAGE_CHANNEL())) {
            boolean verified = isOnLunar(event.getPlayer().getUniqueId());
            plugin.getPlayers().put(event.getPlayer().getUniqueId(), new AbstractMap.SimpleEntry<>(Client.LC, verified));

            if(verified) plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(event.getPlayer(), Client.LC));
            plugin.getWorldHandler().updateWorld(event.getPlayer());
        }
    }

    private boolean isOnLunar(UUID uuid) {
        for(String brand : LunarBreakerAPI.getInstance().getBrands().get(uuid)) {
            if(brand.contains(":") && brand.split(":")[0].equals("lunarclient")) {
                return true;
            }
        }
        return false;
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
        plugin.getBrands().remove(event.getPlayer().getUniqueId());
    }

}
