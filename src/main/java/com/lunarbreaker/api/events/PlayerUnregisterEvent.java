package com.lunarbreaker.api.events;

import com.lunarbreaker.api.client.Client;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called whenever a player unregisters the CB/LC plugin channel
*/
public final class PlayerUnregisterEvent extends Event
{
    @Getter private static final HandlerList handlerList = new HandlerList();

    @Getter private final Player player;
    @Getter private final Client client;

    public PlayerUnregisterEvent(Player player, Client client) {
        this.player = player;
        this.client = client;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}