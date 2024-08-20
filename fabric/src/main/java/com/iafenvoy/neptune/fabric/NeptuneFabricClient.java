package com.iafenvoy.neptune.fabric;

import com.iafenvoy.neptune.NeptuneClient;
import net.fabricmc.api.ClientModInitializer;

public class NeptuneFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NeptuneClient.init();
        NeptuneClient.process();
    }
}
