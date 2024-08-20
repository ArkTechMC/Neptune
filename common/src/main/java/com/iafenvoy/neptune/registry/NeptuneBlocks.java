package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.item.block.WeaponDeskBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;

public class NeptuneBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> WEAPON_DESK = REGISTRY.register("weapon_desk", WeaponDeskBlock::new);
}
