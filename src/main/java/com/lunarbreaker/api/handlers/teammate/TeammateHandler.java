package com.lunarbreaker.api.handlers.teammate;

import com.cheatbreaker.nethandler.server.CBPacketTeammates;
import com.google.common.collect.ImmutableMap;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTeammates;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Used to handle all teammate packets
*/
public class TeammateHandler {

    private final LunarBreakerAPI plugin;
    private final Map<String, Double> posMap = new HashMap<>();

    public TeammateHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player      The player to send the teammates to
     * @param teammates   The teammates to send
     */
    public void sendTeammates(Player player, Teammates teammates) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            Map<UUID, Map<String, Double>> players = new HashMap<>();

            teammates.getPlayers().forEach((p, loc) -> {
                posMap.put("x", loc.getX());
                posMap.put("y", loc.getY());
                posMap.put("z", loc.getZ());
                players.put(p.getUniqueId(), posMap);
            });

            plugin.sendPacket(player, new LCPacketTeammates(
                    teammates.getLeader(),
                    teammates.getLastMs(),
                    players
            ));
            posMap.clear();
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            Map<UUID, Map<String, Double>> players = new HashMap<>();

            teammates.getPlayers().forEach((p, loc) -> {
                posMap.put("x", loc.getX());
                posMap.put("y", loc.getY());
                posMap.put("z", loc.getZ());
                players.put(p.getUniqueId(), posMap);
            });

            plugin.sendPacket(player, new CBPacketTeammates(
                    teammates.getLeader(),
                    teammates.getLastMs(),
                    players
            ));
            posMap.clear();
        }
    }

    /**
     * @param players     The players to send the teammates to
     * @param leader      The team leader
     */
    public void sendTeammates(Collection<Player> players, UUID leader) {
        Teammates teammates = new Teammates(leader, 1, players);
        players.forEach(player -> sendTeammates(player, teammates));
    }

    /**
     * @param players     The players to send the teammates to
     * @param teammates   The teammates to send
     */
    public void sendTeammates(Collection<Player> players, Teammates teammates) {
        players.forEach(player -> sendTeammates(player, teammates));
    }

    /**
     * @param player     The player to remove teammates from
     */
    public void removeTeammates(Player player) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketTeammates(
                    player.getUniqueId(),
                    0,
                    new HashMap<>()
            ));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketTeammates(
                    player.getUniqueId(),
                    0,
                    new HashMap<>()
            ));
        }
    }

    /**
     * @param players     The players to remove teammates from
     */
    public void removeTeammates(Collection<Player> players) {
        players.forEach(this::removeTeammates);
    }

}
