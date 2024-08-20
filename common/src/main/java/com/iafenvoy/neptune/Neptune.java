package com.iafenvoy.neptune;

import com.iafenvoy.neptune.network.ServerNetworkHelper;
import com.iafenvoy.neptune.registry.*;

public class Neptune {
    public static final String MOD_ID = "neptune";

    public static void init() {
        NeptuneBlocks.REGISTRY.register();
        NeptuneItems.REGISTRY.register();
        NeptuneBlockEntities.REGISTRY.register();
        NeptuneScreenHandlers.REGISTRY.register();
        NeptuneRecipes.TYPE_REGISTRY.register();
        NeptuneRecipes.SERIALIZER_REGISTRY.register();
    }

    public static void process() {
        ServerNetworkHelper.register();
    }
}
