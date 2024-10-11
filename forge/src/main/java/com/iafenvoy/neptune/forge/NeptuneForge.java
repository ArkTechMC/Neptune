package com.iafenvoy.neptune.forge;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.NeptuneClient;
import com.iafenvoy.neptune.command.FractionCommand;
import com.iafenvoy.neptune.forge.component.CapabilitySyncHelper;
import com.iafenvoy.neptune.forge.component.NeptunePlayerDataProvider;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Neptune.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeptuneForge {
    public NeptuneForge() {
        EventBuses.registerModEventBus(Neptune.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Neptune.init();
        CapabilitySyncHelper.register(new Identifier(Neptune.MOD_ID, "player_data"), NeptunePlayerDataProvider.CAPABILITY, player -> new NeptunePlayerDataProvider());
        if (Platform.getEnv() == Dist.CLIENT)
            NeptuneClient.init();
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(Neptune::process);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void registerCommand(RegisterCommandsEvent event) {
            event.getDispatcher().register(FractionCommand.FRACTION_COMMAND);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void process(FMLClientSetupEvent event) {
            event.enqueueWork(NeptuneClient::process);
        }
    }
}
