package com.iafenvoy.neptune.forge;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.forge.component.FractionDataProvider;
import com.iafenvoy.neptune.fraction.FractionCommand;
import dev.architectury.platform.Platform;
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
                if ((isServerNotFake || player instanceof AbstractClientPlayerEntity) && !player.getCapability(FractionDataProvider.CAPABILITY).isPresent())
                    event.addCapability(new Identifier(Neptune.MOD_ID, "fraction"), new FractionDataProvider());
            }
        }
    }
}
