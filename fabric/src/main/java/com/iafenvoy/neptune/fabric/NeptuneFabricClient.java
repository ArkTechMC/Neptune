package com.iafenvoy.neptune.fabric;

import com.iafenvoy.neptune.Neptune;
import net.fabricmc.api.ClientModInitializer;

public class NeptuneFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Neptune.initClient();
    }
}
