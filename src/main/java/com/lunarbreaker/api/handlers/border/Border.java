package com.lunarbreaker.api.handlers.border;

import lombok.Getter;

public final class Border {

    @Getter private final String id;
    @Getter private final String world;
    @Getter private final boolean cancelsExit;
    @Getter private final boolean canShrinkExpand;
    @Getter private final int color;
    @Getter private final double minX;
    @Getter private final double minZ;
    @Getter private final double maxX;
    @Getter private final double maxZ;

    /**
     * @param id                The id of the border
     * @param world             The world that the border is located
     * @param cancelsExit       If the border cancels exiting
     * @param canShrinkExpand   If the border can shrink/expand
     * @param color             The color of the border
     * @param minX              The minimum X coordinate
     * @param minZ              The minimum Z coordinate
     * @param maxX              The maximum X coordinate
     * @param maxZ              The maximum Z coordinate
     */
    public Border(String id, String world, boolean cancelsExit, boolean canShrinkExpand, int color, double minX, double minZ, double maxX, double maxZ) {
        this.id = id;
        this.world = world;
        this.cancelsExit = cancelsExit;
        this.canShrinkExpand = canShrinkExpand;
        this.color = color;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

}
