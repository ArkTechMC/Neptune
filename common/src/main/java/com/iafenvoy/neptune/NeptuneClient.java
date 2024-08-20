package com.iafenvoy.neptune;

import com.iafenvoy.neptune.fraction.FractionKeyBinding;
import com.iafenvoy.neptune.registry.NeptuneScreenHandlers;

public class NeptuneClient {
    public static void init() {
        FractionKeyBinding.register();
    }

    public static void process() {
        NeptuneScreenHandlers.initClient();
    }
}
