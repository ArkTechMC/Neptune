package com.iafenvoy.neptune.network;

import com.iafenvoy.neptune.ServerHelper;
import com.iafenvoy.neptune.data.NeptunePlayerData;
import com.iafenvoy.neptune.impl.ComponentManager;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.player.PlayerEntity;

public class ServerNetworkHelper {
    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ServerHelper.FRACTION_ABILITY_PACKET_ID, (buf, context) -> {
            PlayerEntity player = context.getPlayer();
            NeptunePlayerData data = ComponentManager.getPlayerData(player);
            if (data != null) data.getFraction().abilityHandler().accept(buf, player);
        });
    }
}
