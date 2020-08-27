package com.lunarbreaker.api.events;

import com.cheatbreaker.nethandler.CBPacket;
import com.lunarclient.bukkitapi.nethandler.LCPacket;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/*
 * Called whenever the server receives a message the CB/LC plugin channel
*/
public class PacketReceivedEvent extends PlayerEvent implements Cancellable {

    @Getter private static final HandlerList handlerList = new HandlerList();

    @Getter private final CBPacket cbPacket;
    @Getter private final LCPacket lcPacket;

    private boolean cancelled;

    public PacketReceivedEvent(Player who, LCPacket packet) {
        super(who);

        this.lcPacket = packet;
        this.cbPacket = null;
    }

    public PacketReceivedEvent(Player who, CBPacket packet) {
        super(who);

        this.lcPacket = null;
        this.cbPacket = packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

}