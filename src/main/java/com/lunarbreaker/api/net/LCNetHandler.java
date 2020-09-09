package com.lunarbreaker.api.net;

import com.cheatbreaker.nethandler.server.CBPacketVoice;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarbreaker.api.handlers.voice.VoiceChannel;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketClientVoice;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceChannelSwitch;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceMute;
import com.lunarclient.bukkitapi.nethandler.server.LCNetHandlerServer;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketStaffModStatus;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketEmoteBroadcast;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import org.bukkit.entity.Player;

/*
 * LCNetHandler
*/
public class LCNetHandler implements LCNetHandlerServer {
    
    @Override
    public void handleStaffModStatus(LCPacketStaffModStatus packet) {}

    @Override
    public void handleVoice(LCPacketClientVoice packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(player);
        voiceChannel.getPlayersListening().forEach(p -> LunarBreakerAPI.getInstance().sendPacket(p, new CBPacketVoice(player.getUniqueId(), packet.getData())));
    }

    @Override
    public void handleVoiceMute(LCPacketVoiceMute packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(player);
        if(voiceChannel != null) {
            if (player.getUniqueId() == packet.getMuting()) {
                voiceChannel.removeListening(player);
            }
        }
    }

    @Override
    public void handleVoiceChannelSwitch(LCPacketVoiceChannelSwitch packet) {
        Player player = (Player) packet.getAttachment();
        VoiceChannel voiceChannel = LunarBreakerAPI.getInstance().getVoiceHandler().getVoiceChannel(packet.getSwitchingTo());
        if(voiceChannel != null) {
            voiceChannel.addPlayer(player);
        }
    }

    @Override
    public void handleAddWaypoint(LCPacketWaypointAdd packet) {}

    @Override
    public void handleRemoveWaypoint(LCPacketWaypointRemove packet) {}

    @Override
    public void handleEmote(LCPacketEmoteBroadcast packet) {}

}
