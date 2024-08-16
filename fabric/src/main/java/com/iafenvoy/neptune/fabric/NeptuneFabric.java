package com.iafenvoy.neptune.fabric;

import com.iafenvoy.neptune.Neptune;
import net.fabricmc.api.ModInitializer;

public class NeptuneFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Neptune.init();
        Neptune.process();
    }
}