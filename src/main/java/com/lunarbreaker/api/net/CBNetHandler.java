package com.lunarbreaker.api.net;

import com.cheatbreaker.nethandler.client.CBPacketClientVoice;
import com.cheatbreaker.nethandler.client.CBPacketVoiceChannelSwitch;
import com.cheatbreaker.nethandler.client.CBPacketVoiceMute;
import com.cheatbreaker.nethandler.server.CBPacketVoice;
import com.cheatbreaker.nethandler.server.ICBNetHandlerServer;
import com.cheatbreaker.nethandler.shared.CBPacketAddWaypoint;
import com.cheatbreaker.nethandler.shared.CBPacketRemoveWaypoint;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarbreaker.api.handlers.voice.VoiceChannel;
import org.bukkit.entity.Player;

/*
 * CBNetHandler
*/
public class CBNetHandler implements ICBNetHandlerServer {

    @Override
    public void handleVoice(CBPacketClientVoice packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(player);
        voiceChannel.getPlayersListening().forEach(p -> LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoice(player.getUniqueId(), packet.getData())));
    }

    @Override
    public void handleVoiceChannelSwitch(CBPacketVoiceChannelSwitch packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(packet.getSwitchingTo());
        if(voiceChannel != null) {
            voiceChannel.addPlayer(player);
        }
    }

    @Override
    public void handleVoiceMute(CBPacketVoiceMute packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(player);
        if(voiceChannel != null) {
            if (player.getUniqueId() == packet.getMuting()) {
                voiceChannel.removeListening(player);
            }
        }
    }

    @Override
    public void handleAddWaypoint(CBPacketAddWaypoint cbPacketAddWaypoint) {}

    @Override
    public void handleRemoveWaypoint(CBPacketRemoveWaypoint cbPacketRemoveWaypoint) {}

}
