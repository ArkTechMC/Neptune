package com.iafenvoy.neptune.fraction;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.ServerHelper;
import com.iafenvoy.neptune.network.PacketBufferUtils;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FractionKeyBinding {
    public static final KeyBinding FRACTION_ABILITY = new KeyBinding("key." + Neptune.MOD_ID + ".fraction_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category." + Neptune.MOD_ID);

    public static void register() {
        KeyMappingRegistry.register(FRACTION_ABILITY);
        ClientTickEvent.CLIENT_POST.register(client -> {
            if (FRACTION_ABILITY.wasPressed())
                NetworkManager.sendToServer(ServerHelper.FRACTION_ABILITY_PACKET_ID, PacketBufferUtils.create());
        });
    }
}
