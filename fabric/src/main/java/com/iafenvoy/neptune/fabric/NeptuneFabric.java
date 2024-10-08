package com.iafenvoy.neptune.fabric;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.command.FractionCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class NeptuneFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Neptune.init();
        Neptune.process();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(FractionCommand.FRACTION_COMMAND));
    }
}