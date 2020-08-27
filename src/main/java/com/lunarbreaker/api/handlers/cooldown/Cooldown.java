package com.lunarbreaker.api.handlers.cooldown;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.Material;

import java.time.Duration;

/*
 * Used to build a cooldown packet
*/
public final class Cooldown {

    @Getter private final String message;
    @Getter private final long durationMs;
    @Getter private final Material icon;

    /**
     * @param name   The name of the cooldown
     * @param time   The amount of units for the cooldown
     * @param icon   The material of the cooldown
     */
    public Cooldown(String name, Duration time, Material icon) {
        this.message = Preconditions.checkNotNull(name, "message");
        this.durationMs = time.toMillis();
        this.icon = Preconditions.checkNotNull(icon, "icon");
    }

}