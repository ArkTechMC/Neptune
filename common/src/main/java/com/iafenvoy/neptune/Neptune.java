package com.iafenvoy.neptune;

import com.iafenvoy.neptune.fraction.FractionKeyBinding;
import com.iafenvoy.neptune.network.ServerNetworkHelper;

public class Neptune {
    public static final String MOD_ID = "neptune";

    public static void init() {
    }

    public static void process() {
        ServerNetworkHelper.register();
    }

    public static void initClient() {
        FractionKeyBinding.register();
    }
}
