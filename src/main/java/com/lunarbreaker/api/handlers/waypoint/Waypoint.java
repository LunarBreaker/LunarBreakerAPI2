package com.lunarbreaker.api.handlers.waypoint;

import com.lunarbreaker.api.LunarBreakerAPI;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Location;

/*
 * Used to build a waypoint packet
*/
public final class Waypoint {

    @Getter private final String name;
    @Getter private final int x;
    @Getter private final int y;
    @Getter private final int z;
    @Getter private final String world;
    @Getter private final int color;
    @Getter private final boolean forced;
    @Getter private final boolean visible;

    /**
     * @param name      The name of the waypoint
     * @param x         The X coordinate of the waypoint
     * @param y         The Y coordinate of the waypoint
     * @param z         The Z coordinate of the waypoint
     * @param world     The world of the waypoint
     * @param color     The color of the waypoint
     * @param forced    If the waypoint is forced
     * @param visible   If the waypoint is visible
     */
    public Waypoint(String name, int x, int y, int z, String world, int color, boolean forced, boolean visible) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.color = color;
        this.forced = forced;
        this.visible = visible;
    }

    /**
     * @param name       The name of the waypoint
     * @param location   The location of the waypoint
     * @param color     The color of the waypoint
     * @param forced    If the waypoint is forced
     * @param visible   If the waypoint is visible
     */
    public Waypoint(String name, Location location, int color, boolean forced, boolean visible) {
        this(
            name,
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ(),
            LunarBreakerAPI.getInstance().getWorldHandler().getWorldIdentifier(location.getWorld()),
            color,
            forced,
            visible
        );
    }

}