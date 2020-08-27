package com.lunarbreaker.api.handlers.voice;

import com.cheatbreaker.nethandler.server.CBPacketDeleteVoiceChannel;
import com.cheatbreaker.nethandler.server.CBPacketServerRule;
import com.cheatbreaker.nethandler.server.CBPacketVoiceChannel;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerRule;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannel;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelRemove;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/*
 * Used to handle all voice packets
*/
public class VoiceHandler {

    private final LunarBreakerAPI plugin;

    @Getter private final List<VoiceChannel> voiceChannels;

    public VoiceHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
        voiceChannels = new ArrayList<>();
    }

    /**
     * @param player      The player to update voice chat for
     * @param isEnabled   The voice chat status
     */
    public void setVoiceChat(Player player, boolean isEnabled) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketServerRule(com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule.VOICE_ENABLED, isEnabled));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketServerRule(com.cheatbreaker.nethandler.obj.ServerRule.VOICE_ENABLED, isEnabled));
        }
    }

    /**
     * @param player    The player that is receiving the voice channel
     * @param channel   The channel the player is receiving
     */
    public void sendVoiceChannel(Player player, VoiceChannel channel) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketVoiceChannel(channel.getUuid(), channel.getName(), channel.toPlayersMap(), channel.toListeningMap()));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketVoiceChannel(channel.getUuid(), channel.getName(), channel.toPlayersMap(), channel.toListeningMap()));
        }
    }

    /**
     * @param voiceChannels   The channel(s) to be created
     */
    public void createChannels(VoiceChannel... voiceChannels) {
        this.voiceChannels.addAll(Arrays.asList(voiceChannels));
        for (VoiceChannel channel : voiceChannels) {
            for (Player player : channel.getPlayersInChannel()) {
                sendVoiceChannel(player, channel);
            }
        }
    }

    /**
     * @param uuid   The uuid to search for
     * @return       The voice channel with the selected uuid
     */
    public VoiceChannel getVoiceChannel(UUID uuid) {
        return voiceChannels.stream().filter(voiceChannel -> voiceChannel.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * @param player   The player to search for
     * @return         The voice channel with the selected player
     */
    public VoiceChannel getVoiceChannel(Player player) {
        return voiceChannels.stream().filter(voiceChannel -> voiceChannel.hasPlayer(player)).findFirst().orElse(null);
    }

    /**
     * @param channel   The voice channel to say
     */
    public void deleteVoiceChannel(VoiceChannel channel) {
        this.voiceChannels.removeIf(c -> {
            boolean remove = c == channel;
            if (remove) {
                for (Player player : channel.getPlayersInChannel()) {
                    if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
                        plugin.sendPacket(player, new CBPacketDeleteVoiceChannel(channel.getUuid()));
                    }else if(plugin.isRunningLunarClient(player.getUniqueId())) {
                        plugin.sendPacket(player, new LCPacketVoiceChannelRemove(channel.getUuid()));
                    }
                    VoiceChannel voiceChannel = getVoiceChannel(player);
                    if (voiceChannel != null && voiceChannel == channel) {
                        channel.removePlayer(player);
                    }
                }
            }
            return remove;
        });
    }

    /**
     * @param channelUUID   The voice channel uuid to delete
     */
    public void deleteVoiceChannel(UUID channelUUID) {
        VoiceChannel voiceChannel = getVoiceChannel(channelUUID);
        if(voiceChannel != null) deleteVoiceChannel(voiceChannel);
    }

}
