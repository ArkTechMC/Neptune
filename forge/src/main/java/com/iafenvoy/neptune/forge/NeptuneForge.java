package com.iafenvoy.neptune.forge;

import com.iafenvoy.neptune.Neptune;
import dev.architectury.platform.Platform;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Neptune.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeptuneForge {
    public NeptuneForge() {
        Neptune.init();
        if (Platform.getEnv() == Dist.CLIENT)
            Neptune.initClient();
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(Neptune::process);
    }
}
