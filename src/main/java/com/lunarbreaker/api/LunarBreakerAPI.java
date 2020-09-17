package com.lunarbreaker.api;

import com.cheatbreaker.nethandler.CBPacket;
import com.google.common.base.Charsets;
import com.lunarbreaker.api.client.Client;
import com.lunarbreaker.api.commands.CBCommand;
import com.lunarbreaker.api.commands.ClientCommand;
import com.lunarbreaker.api.commands.LBCommand;
import com.lunarbreaker.api.commands.LCCommand;
import com.lunarbreaker.api.events.PacketReceivedEvent;
import com.lunarbreaker.api.events.PacketSentEvent;
import com.lunarbreaker.api.handlers.border.BorderHandler;
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
import lombok.Setter;
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
    @Getter private static final List<String> FORGE_MESSAGE_CHANNELS = new ArrayList<>();

    static {
        FORGE_MESSAGE_CHANNELS.add("FML|HS");
        FORGE_MESSAGE_CHANNELS.add("FML");
        FORGE_MESSAGE_CHANNELS.add("FORGE");
    }

    @Getter private BorderHandler borderHandler;
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

    @Setter @Getter private CBNetHandler cbNetHandlerServer = new CBNetHandler();
    @Setter @Getter private LCNetHandler lcNetHandlerServer = new LCNetHandler();

    @Getter private final Map<UUID, Map.Entry<Client, Boolean>> players = new HashMap<>();
    @Getter private final Map<UUID, List<String>> brands = new HashMap<>();
    @Getter private final Map<UUID, List<String>> channels = new HashMap<>();

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

        messenger.registerIncomingPluginChannel(this, "MC|Brand", (channel, player, bytes) -> {
            if(!brands.containsKey(player.getUniqueId())) {
                brands.put(player.getUniqueId(), new ArrayList<>());
            }
            List<String> brands = this.brands.get(player.getUniqueId());
            String brand = isOn18(player) ? new String(bytes, Charsets.UTF_8).replaceFirst("\u0013", "") : new String(bytes, Charsets.UTF_8);

            if(!brands.contains(brand)) {
                brands.add(brand);
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

        borderHandler = new BorderHandler(this);
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
     * @return       True if they are running Forge
     */
    public boolean isRunningForge(UUID uuid) {
        return players.containsKey(uuid) && players.get(uuid).equals(new AbstractMap.SimpleEntry<>(Client.FORGE, true));
    }

    /**
     * @param uuid   The UUID of the player you are checking
     * @return       True if they are running CB
     */
    public boolean isRunningCheatBreaker(UUID uuid) {
        return players.containsKey(uuid) && players.get(uuid).equals(new AbstractMap.SimpleEntry<>(Client.CB, true));
    }

    /**
     * @param uuid   The UUID of the player you are checking
     * @return       True if they are running LC
     */
    public boolean isRunningLunarClient(UUID uuid) {
        return players.containsKey(uuid) && players.get(uuid).equals(new AbstractMap.SimpleEntry<>(Client.LC, true));
    }

    /**
     * @param p   The player you are checking
     * @return    True if they are running Forge
     */
    @Deprecated
    public boolean isRunningForge(Player p) {
        return isRunningForge(p.getUniqueId());
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
     * @param player   The player you are checking
     * @return         True if they are running 1.8 or higher
     */
    public boolean isOn18(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion() >= 47;
    }

    /**
     * @param player   The player you are checking
     * @return         The version of lunar client they are on
     */
    public String getLunarVersion(Player player) {
        for(String brand : LunarBreakerAPI.getInstance().getBrands().get(player.getUniqueId())) {
            if(brand.contains(":")) {
                String[] splitBrand = brand.split(":");
                if(brand.split(":")[0].equals("lunarclient") && splitBrand[1].length() == 7) {
                    return splitBrand[1];
                }
            }
        }
        return "N/A";
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

    /**
     * @param r   Red value
     * @param g   Green value
     * @param b   Blue Value
     * @return    Decimal color value
     */
    public int fromRGB(int r, int g, int b) {
        return ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

}
