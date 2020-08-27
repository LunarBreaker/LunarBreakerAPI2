package com.lunarbreaker.api;

import com.cheatbreaker.nethandler.CBPacket;
import com.lunarbreaker.api.client.Client;
import com.lunarbreaker.api.commands.CBCommand;
import com.lunarbreaker.api.commands.ClientCommand;
import com.lunarbreaker.api.commands.LBCommand;
import com.lunarbreaker.api.commands.LCCommand;
import com.lunarbreaker.api.events.PacketReceivedEvent;
import com.lunarbreaker.api.events.PacketSentEvent;
import com.lunarbreaker.api.handlers.bossbar.BossbarHandler;
import com.lunarbreaker.api.handlers.cooldown.CooldownHandler;
import com.lunarbreaker.api.handlers.emote.EmoteHandler;
import com.lunarbreaker.api.handlers.ghost.GhostHandler;
import com.lunarbreaker.api.handlers.hologram.HologramHandler;
import com.lunarbreaker.api.handlers.nametag.NametagHandler;
import com.lunarbreaker.api.handlers.notification.NotificationHandler;
import com.lunarbreaker.api.handlers.server.ServerHandler;
import com.lunarbreaker.api.handlers.staffmodule.StaffModuleHandler;
import com.lunarbreaker.api.handlers.teammate.TeammateHandler;
import com.lunarbreaker.api.handlers.title.TitleHandler;
import com.lunarbreaker.api.handlers.voice.VoiceHandler;
import com.lunarbreaker.api.handlers.waypoint.WaypointHandler;
import com.lunarbreaker.api.handlers.world.WorldHandler;
import com.lunarbreaker.api.listeners.PlayerListener;
import com.lunarbreaker.api.net.CBNetHandler;
import com.lunarbreaker.api.net.LCNetHandler;
import com.lunarclient.bukkitapi.nethandler.LCPacket;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.*;

public class LunarBreakerAPI extends JavaPlugin {

    @Getter private static LunarBreakerAPI instance;

    @Getter private static final String CB_MESSAGE_CHANNEL = "CB-Client";
    @Getter private static final String LC_MESSAGE_CHANNEL = "Lunar-Client";

    @Getter private BossbarHandler bossbarHandler;
    @Getter private CooldownHandler cooldownHandler;
    @Getter private EmoteHandler emoteHandler;
    @Getter private GhostHandler ghostHandler;
    @Getter private HologramHandler hologramHandler;
    @Getter private NametagHandler nametagHandler;
    @Getter private NotificationHandler notificationHandler;
    @Getter private ServerHandler serverHandler;
    @Getter private StaffModuleHandler staffModuleHandler;
    @Getter private TeammateHandler teammateHandler;
    @Getter private TitleHandler titleHandler;
    @Getter private VoiceHandler voiceHandler;
    @Getter private WaypointHandler waypointHandler;
    @Getter private WorldHandler worldHandler;

    @Getter private final CBNetHandler cbNetHandlerServer = new CBNetHandler();
    @Getter private final LCNetHandler lcNetHandlerServer = new LCNetHandler();

    @Getter private final Map<UUID, Client> players = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, CB_MESSAGE_CHANNEL);
        messenger.registerOutgoingPluginChannel(this, LC_MESSAGE_CHANNEL);

        messenger.registerIncomingPluginChannel(this, CB_MESSAGE_CHANNEL, (channel, player, bytes) -> {
            CBPacket packet = CBPacket.handle(cbNetHandlerServer, bytes, player);
                    if(packet != null) {
                        PacketReceivedEvent event = new PacketReceivedEvent(player, packet);
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            packet.process(this.cbNetHandlerServer);
                        }
                    }
        });

        messenger.registerIncomingPluginChannel(this, LC_MESSAGE_CHANNEL, (channel, player, bytes) -> {
            LCPacket packet = LCPacket.handle(bytes, player);
            if(packet != null) {
                PacketReceivedEvent event = new PacketReceivedEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    packet.process(this.lcNetHandlerServer);
                }
            }
        });

        getCommand("lc").setExecutor(new LCCommand());
        getCommand("client").setExecutor(new ClientCommand());
        getCommand("lunarbreaker").setExecutor(new LBCommand());
        getCommand("cb").setExecutor(new CBCommand());

        bossbarHandler = new BossbarHandler(this);
        cooldownHandler = new CooldownHandler(this);
        emoteHandler = new EmoteHandler(this);
        ghostHandler = new GhostHandler(this);
        hologramHandler = new HologramHandler(this);
        nametagHandler = new NametagHandler(this);
        notificationHandler = new NotificationHandler(this);
        serverHandler = new ServerHandler(this);
        staffModuleHandler = new StaffModuleHandler(this);
        teammateHandler = new TeammateHandler(this);
        titleHandler = new TitleHandler(this);
        voiceHandler = new VoiceHandler(this);
        waypointHandler = new WaypointHandler(this);
        worldHandler = new WorldHandler(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    /**
     * @param uuid   The UUID of the player you are checking
     * @return       True if they are running CB
     */
    public boolean isRunningCheatBreaker(UUID uuid) {
        return players.containsKey(uuid) && players.get(uuid).equals(Client.CB);
    }

    /**
     * @param uuid   The UUID of the player you are checking
     * @return       True if they are running LC
     */
    public boolean isRunningLunarClient(UUID uuid) {
        return players.containsKey(uuid) && players.get(uuid).equals(Client.LC);
    }

    /**
     * @param p   The player you are checking
     * @return    True if they are running CB
     */
    @Deprecated
    public boolean isRunningCheatBreaker(Player p) {
        return isRunningCheatBreaker(p.getUniqueId());
    }

    /**
     * @param p   The player you are checking
     * @return    True if they are running LC
     */
    @Deprecated
    public boolean isRunningLunarClient(Player p) {
        return isRunningLunarClient(p.getUniqueId());
    }

    /**
     * @param p   The player you are checking
     * @return    True if they are running 1.8 or higher
     */
    public boolean isOn18(Player p) {
        return ((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47;
    }

    /**
     * Attempts to send the CB message and returns if successful
     *
     * @param player   The player the message is sent to
     * @param packet   The CB packet message
     * @return         True if the message is sent
     */
    public boolean sendPacket(Player player, @NonNull CBPacket packet) {
        if(isRunningCheatBreaker(player.getUniqueId())) {
            player.sendPluginMessage(this, CB_MESSAGE_CHANNEL, CBPacket.getPacketData(packet));
            Bukkit.getPluginManager().callEvent(new PacketSentEvent(player, packet));
            return true;
        }
        return false;
    }

    /**
     * Attempts to send the LC message and returns if successful
     *
     * @param player   The player the message is sent to
     * @param packet   The LC packet message
     * @return         True if the message is sent
     */
    public boolean sendPacket(Player player, LCPacket packet) {
        if(isRunningLunarClient(player.getUniqueId())) {
            player.sendPluginMessage(this, LC_MESSAGE_CHANNEL, LCPacket.getPacketData(packet));
            Bukkit.getPluginManager().callEvent(new PacketSentEvent(player, packet));
            return true;
        }
        return false;
    }

}
