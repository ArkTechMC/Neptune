package com.iafenvoy.neptune;

import com.iafenvoy.neptune.network.ServerNetworkHelper;
import com.iafenvoy.neptune.registry.*;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;

public class Neptune {
    public static final String MOD_ID = "neptune";

    public static void init() {
        NeptuneBlocks.REGISTRY.register();
        NeptuneItems.REGISTRY.register();
        NeptuneBlockEntities.REGISTRY.register();
        NeptuneScreenHandlers.REGISTRY.register();
        NeptuneRecipes.TYPE_REGISTRY.register();
        NeptuneRecipes.SERIALIZER_REGISTRY.register();

        if (!Platform.isModLoaded("sponsor_core"))
            throw new RuntimeException("Cannot find Sponsor Core, please re-download " + MOD_ID + " or contact author.");
    }

    public static void process() {
        ServerNetworkHelper.register();
        CreativeTabRegistry.appendBuiltin(Registries.ITEM_GROUP.get(ItemGroups.FUNCTIONAL), NeptuneBlocks.WEAPON_DESK.get());
    }
}
