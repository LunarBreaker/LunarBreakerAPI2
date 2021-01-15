package com.lunarbreaker.api.handlers.teammate;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Used to build a teammate packet
*/
public final class Teammates {

    @Getter private final UUID leader;
    @Getter private final long lastMs;
    @Getter private final Map<Player, Location> players;

    /**
     * @param leader    The UUID of the leader
     * @param lastMs    The last ping
     * @param players   The list of players in the team
     */
    public Teammates(UUID leader, long lastMs, Collection<Player> players) {
        this.leader = leader;
        this.lastMs = lastMs;
        this.players = new HashMap<>();
        players.forEach(p -> {
            if(p != null) {
                this.players.put(p, p.getLocation());
            }
        });
    }

}
