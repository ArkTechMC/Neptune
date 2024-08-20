package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

public class NeptuneItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> WEAPON_DESK = REGISTRY.register("weapon_desk", () -> new BlockItem(NeptuneBlocks.WEAPON_DESK.get(), new Item.Settings()));
}
