package com.iafenvoy.neptune.event;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerEvents {
    public static final Event<PlayerLoggedInOrOut> LOGGED_OUT = new Event<>(callbacks -> player -> {
        for (PlayerLoggedInOrOut e : callbacks)
            e.handleConnection(player);
    });

    /**
     * Use this interface to handle players logging in and out.
     */
    @FunctionalInterface
    public interface PlayerLoggedInOrOut {
        void handleConnection(PlayerEntity player);
    }
}
