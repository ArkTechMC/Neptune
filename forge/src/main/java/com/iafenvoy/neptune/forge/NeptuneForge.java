package com.iafenvoy.neptune.forge;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.NeptuneClient;
import com.iafenvoy.neptune.command.FractionCommand;
import com.iafenvoy.neptune.forge.component.NeptunePlayerDataProvider;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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

        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity player) {
                boolean isServerNotFake = player instanceof ServerPlayerEntity && !(player instanceof FakePlayer);
                if ((isServerNotFake || player instanceof AbstractClientPlayerEntity) && !player.getCapability(NeptunePlayerDataProvider.CAPABILITY).isPresent())
                    event.addCapability(new Identifier(Neptune.MOD_ID, "player_data"), new NeptunePlayerDataProvider());
            }
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
