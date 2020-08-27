package com.lunarbreaker.api.handlers.notification;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.time.Duration;

/*
 * Used to build a notification packet
*/
public final class Notification {

    @Getter private final String message;
    @Getter private final long durationMs;
    @Getter private final Level level;

    /**
     * @param message   The message the notification contains
     * @param time      The duration the notification will be shown for
     */
    public Notification(String message, Duration time) {
        this(message, time, Level.INFO);
    }

    /**
     * @param message   The message the notification contains
     * @param time      The duration the notification will be shown for
     * @param level     The level of the notification
     */
    public Notification(String message, Duration time, Level level) {
        this.message = Preconditions.checkNotNull(message, "message");
        this.durationMs = time.toMillis();
        this.level = Preconditions.checkNotNull(level, "level");
    }

    /*
     * Used to get all supported notification levels
     */
    public enum Level {
        INFO, ERROR
    }

}