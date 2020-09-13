package com.lunarbreaker.api.listeners;

import com.google.common.base.Charsets;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarbreaker.api.client.Client;
import com.lunarbreaker.api.events.PlayerRegisterEvent;
import com.lunarbreaker.api.events.PlayerUnregisterEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
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
        Player player = event.getPlayer();

        if(!plugin.getChannels().containsKey(player.getUniqueId())) {
            plugin.getChannels().put(player.getUniqueId(), new ArrayList<>());
        }
        List<String> channels = plugin.getChannels().get(player.getUniqueId());
        String channel = event.getChannel();
        if(!channels.contains(channel)) {
            channels.add(channel);
        }

        if(channel.equals(LunarBreakerAPI.getCB_MESSAGE_CHANNEL())) {
            boolean verified = LunarBreakerAPI.getInstance().getBrands().get(player.getUniqueId()).contains("vanilla") && !LunarBreakerAPI.getInstance().isOn18(player);
            plugin.getPlayers().put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Client.CB, verified));

            if(verified) plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player, Client.CB));
            plugin.getWorldHandler().updateWorld(event.getPlayer());
        }else if(channel.equals(LunarBreakerAPI.getLC_MESSAGE_CHANNEL())) {
            boolean verified = isOnLunar(player.getUniqueId());
            plugin.getPlayers().put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Client.LC, verified));

            if(verified) plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player, Client.LC));
            plugin.getWorldHandler().updateWorld(player);
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
        plugin.getChannels().remove(event.getPlayer().getUniqueId());
    }

}
