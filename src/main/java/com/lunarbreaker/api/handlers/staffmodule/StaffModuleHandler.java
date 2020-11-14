package com.lunarbreaker.api.handlers.staffmodule;

import com.cheatbreaker.nethandler.server.CBPacketStaffModState;
import com.lunarbreaker.api.LunarBreakerAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketStaffModState;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketStaffModStatus;
import org.bukkit.entity.Player;

import java.util.Collections;

/*
 * Used to handle all staff module packets
*/
public class StaffModuleHandler {

    private final LunarBreakerAPI plugin;

    public StaffModuleHandler(LunarBreakerAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player   The player to update the staff module state for
     * @param module   The module to update
     * @param state    The state of the module
     */
    public void setStaffModuleState(Player player, StaffModule module, boolean state) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketStaffModState(module.name(), state));
        }else if(plugin.isRunningCheatBreaker(player.getUniqueId())) {
            plugin.sendPacket(player, new CBPacketStaffModState(module.name(), state));
        }
    }

    /**
     * @param player   The player to give all staff modules to
     */
    public void giveAllStaffModules(Player player) {
        if(plugin.isRunningLunarClient(player.getUniqueId())) {
            plugin.sendPacket(player, new LCPacketStaffModStatus(Collections.singleton("BUNNY_HOP")));
        }
    }

    /**
     * @param player   The player to remove all staff modules from
     */
    public void disableAllStaffModules(Player player) {
        if(plugin.isRunningCheatBreaker(player.getUniqueId()) || plugin.isRunningLunarClient(player.getUniqueId())) {
            for (StaffModule module : StaffModule.values()) {
                setStaffModuleState(player, module, false);
            }
        }
    }

}
