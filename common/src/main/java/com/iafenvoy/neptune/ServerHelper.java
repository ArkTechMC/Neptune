package com.iafenvoy.neptune;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ServerHelper {
    @Nullable
    public static MinecraftServer server = null;
    public static final Identifier FRACTION_ABILITY_PACKET_ID = Identifier.of(Neptune.MOD_ID, "fraction_ability_hotkey");
}
