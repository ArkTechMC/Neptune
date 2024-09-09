package com.iafenvoy.neptune;

import com.iafenvoy.neptune.fraction.FractionKeyBinding;
import com.iafenvoy.neptune.object.item.CanActiveSwordItem;
import com.iafenvoy.neptune.registry.NeptuneScreenHandlers;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.util.Identifier;

public class NeptuneClient {
    public static void init() {
        FractionKeyBinding.register();
    }

    public static void process() {
        NeptuneScreenHandlers.initClient();
        ItemPropertiesRegistry.registerGeneric(new Identifier(Neptune.MOD_ID, CanActiveSwordItem.ACTIVE_KEY), (stack, world, entity, seed) -> stack.getNbt() != null && stack.getNbt().getBoolean(CanActiveSwordItem.ACTIVE_KEY) ? 1 : 0);
    }
}
