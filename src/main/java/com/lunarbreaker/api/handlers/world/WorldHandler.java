package com.lunarbreaker.api.handlers.world;

import com.cheatbreaker.nethandler.server.CBPacketUpdateWorld;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/*
 * Used to handle all world packets
*/
public class WorldHandler implements Listener {

    private final LunarBreakerAPI plugin;
    private final Map<UUID, Function<World, String>> worldIdentifiers = new HashMap<>();

    public WorldHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        updateWorld(e.getPlayer());
    }

    public void updateWorld(Player player) {
        String worldIdentifier = getWorldIdentifier(player.getWorld());

        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketUpdateWorld(worldIdentifier));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketUpdateWorld(worldIdentifier));
        }
    }

    /**
     * @param world   The world to get the identifier of
     * @return        The world identifier
     */
    public String getWorldIdentifier(World world) {
        String worldIdentifier = world.getUID().toString();

        if (worldIdentifiers.containsKey(world.getUID())) {
            worldIdentifier = worldIdentifiers.get(world.getUID()).apply(world);
        }

        return worldIdentifier;
    }

    /**
     * @param world        The world to register
     * @param identifier   The identifier
     */
    public void registerWorldIdentifier(World world, Function<World, String> identifier) {
        worldIdentifiers.put(world.getUID(), identifier);
    }

}
