package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.item.block.entity.WeaponDeskBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;

public class NeptuneBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<WeaponDeskBlockEntity>> WEAPON_DESK = REGISTRY.register("weapon_desk", () -> BlockEntityType.Builder.create(WeaponDeskBlockEntity::new, NeptuneBlocks.WEAPON_DESK.get()).build(null));
}
