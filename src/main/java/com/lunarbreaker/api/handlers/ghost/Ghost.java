package com.lunarbreaker.api.handlers.ghost;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public final class Ghost {

    private final List<UUID> ghostedPlayers;
    private final List<UUID> unGhostedPlayers;

    /**
     * @param ghostedPlayers     The players to ghost
     * @param unGhostedPlayers   The players to unghost
     */
    public Ghost(List<UUID> ghostedPlayers, List<UUID> unGhostedPlayers) {
        this.ghostedPlayers = ghostedPlayers;
        this.unGhostedPlayers = unGhostedPlayers;
    }

}
