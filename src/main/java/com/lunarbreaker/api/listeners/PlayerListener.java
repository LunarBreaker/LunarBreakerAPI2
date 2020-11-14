package com.lunarbreaker.api.listeners;

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

import java.util.ArrayList;
import java.util.List;

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

        boolean verified;

        switch(channel) {
            case LunarBreakerAPI.CB_MESSAGE_CHANNEL:
                verified = LunarBreakerAPI.getInstance().getBrands().get(player.getUniqueId()).contains("vanilla") && LunarBreakerAPI.getInstance().getVersion(player).equals("1.7");
                if(!verified) break;
                plugin.getPlayers().put(player.getUniqueId(), Client.CB);

                plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player, Client.CB));
                plugin.getWorldHandler().updateWorld(event.getPlayer());
                break;
            case LunarBreakerAPI.LC_MESSAGE_CHANNEL:
                verified = !plugin.getLunarVersion(player).equals("N/A");
                if(!verified) break;
                plugin.getPlayers().put(player.getUniqueId(), Client.LC);

                plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player, Client.LC));
                plugin.getWorldHandler().updateWorld(player);
                return;
        }

        if(LunarBreakerAPI.FORGE_MESSAGE_CHANNELS.contains(channel) && plugin.getChannels().get(player.getUniqueId()).containsAll(LunarBreakerAPI.FORGE_MESSAGE_CHANNELS)) {
            plugin.getPlayers().put(player.getUniqueId(), Client.FORGE);

            plugin.getServer().getPluginManager().callEvent(new PlayerRegisterEvent(player, Client.FORGE));
        }
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        switch (event.getChannel()) {
            case LunarBreakerAPI.CB_MESSAGE_CHANNEL:
                plugin.getPlayers().remove(event.getPlayer().getUniqueId());

                plugin.getServer().getPluginManager().callEvent(new PlayerUnregisterEvent(event.getPlayer(), Client.CB));
                break;
            case LunarBreakerAPI.LC_MESSAGE_CHANNEL:
                plugin.getPlayers().remove(event.getPlayer().getUniqueId());

                plugin.getServer().getPluginManager().callEvent(new PlayerUnregisterEvent(event.getPlayer(), Client.LC));
                break;
        }
    }

    @EventHandler
    public void onUnregister(PlayerQuitEvent event) {
        if(plugin.isRunningLunarClient(event.getPlayer().getUniqueId()) && (!plugin.getVersion(event.getPlayer()).equals("1.7") && !plugin.getVersion(event.getPlayer()).equals("1.8"))) {
            plugin.getPlayers().remove(event.getPlayer().getUniqueId());

            plugin.getServer().getPluginManager().callEvent(new PlayerUnregisterEvent(event.getPlayer(), Client.LC));
        }

        plugin.getPlayers().remove(event.getPlayer().getUniqueId());
        plugin.getBrands().remove(event.getPlayer().getUniqueId());
        plugin.getChannels().remove(event.getPlayer().getUniqueId());
    }

}
