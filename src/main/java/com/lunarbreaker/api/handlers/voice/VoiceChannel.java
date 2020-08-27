package com.lunarbreaker.api.handlers.voice;

import com.cheatbreaker.nethandler.server.CBPacketDeleteVoiceChannel;
import com.cheatbreaker.nethandler.server.CBPacketVoiceChannelUpdate;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelUpdate;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class VoiceChannel {
    
    private final String name;
    private final UUID uuid;
    private final List<Player> playersInChannel = new ArrayList<>();
    private final List<Player> playersListening = new ArrayList<>();

    /**
     * @param name   The name of the voice channel
     */
    public VoiceChannel(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    /**
     * @param player   The player to add to the voice channel
     */
    public void addPlayer(Player player) {
        if(hasPlayer(player)) return;

        playersInChannel.add(player);

        playersInChannel.forEach(p -> {
            if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoiceChannelUpdate(0, uuid, player.getUniqueId(), player.getName()));
            }else if(LunarBreakerAPI.getInstance().isRunningLunarClient(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new LCPacketVoiceChannelUpdate(0, uuid, player.getUniqueId(), player.getName()));
            }
        });

        addListening(player);
    }

    /**
     * @param player   The that is going to be listening
     */
    public void addListening(Player player) {
        if(!hasPlayer(player) && !hasPlayerListening(player)) return;

        playersInChannel.forEach(p -> {
            if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoiceChannelUpdate(2, uuid, player.getUniqueId(), player.getName()));
            }else if(LunarBreakerAPI.getInstance().isRunningLunarClient(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new LCPacketVoiceChannelUpdate(2, uuid, player.getUniqueId(), player.getName()));
            }
        });

        playersListening.add(player);
    }

    /**
     * @param player   The player that is going to be removed from the voice channel
     */
    public void removePlayer(Player player) {
        if(!hasPlayer(player)) return;

        playersInChannel.forEach(p -> {
            if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoiceChannelUpdate(1, uuid, player.getUniqueId(), player.getName()));
            }else if(LunarBreakerAPI.getInstance().isRunningLunarClient(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new LCPacketVoiceChannelUpdate(1, uuid, player.getUniqueId(), player.getName()));
            }
        });

        playersInChannel.remove(player);
        LunarBreakerAPI.getInstance().sendPacket(player, new CBPacketDeleteVoiceChannel(uuid));

        removeListening(player);
        player.sendMessage(ChatColor.AQUA + "Left " + name + " channel.");
    }

    /**
     * @param player   The player that is no longer going to be listening
     */
    public void removeListening(Player player) {
        if(!hasPlayer(player) && hasPlayerListening(player)) return;

        playersInChannel.forEach(p -> {
            if(LunarBreakerAPI.getInstance().isRunningCheatBreaker(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoiceChannelUpdate(3, uuid, player.getUniqueId(), player.getName()));
            }else if(LunarBreakerAPI.getInstance().isRunningLunarClient(p.getUniqueId())) {
                LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoiceChannelUpdate(3, uuid, player.getUniqueId(), player.getName()));
            }
        });

        playersListening.remove(player);
    }

    /**
     * @param player   The player that is going to be checked
     * @return         True if the player is in the channel
     */
    public boolean hasPlayer(Player player) {
        return playersInChannel.contains(player);
    }

    /**
     * @param player   The player that is going to be checked
     * @return         True if the player is listening
     */
    public boolean hasPlayerListening(Player player) {
        return playersListening.contains(player);
    }

    public Map<UUID, String> toPlayersMap() {
        return playersInChannel.stream()
                .collect(Collectors.toMap(
                        Player::getUniqueId,
                        Player::getName
                ));
    }

    public Map<UUID, String> toListeningMap() {
        return playersListening.stream()
                .collect(Collectors.toMap(
                        Player::getUniqueId,
                        Player::getName
                ));
    }
}
