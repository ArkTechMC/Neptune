package com.iafenvoy.neptune.impl.forge;

import com.iafenvoy.neptune.forge.component.FractionDataProvider;
import com.iafenvoy.neptune.forge.component.FractionDataStorage;
import com.iafenvoy.neptune.fraction.FractionData;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static FractionData getFractionData(LivingEntity entity) {
        LazyOptional<FractionDataStorage> storageLazyOptional = entity.getCapability(FractionDataProvider.CAPABILITY);
        if (!storageLazyOptional.isPresent()) return null;
        Optional<FractionDataStorage> storage = storageLazyOptional.resolve();
        return storage.map(FractionDataStorage::getPlayerData).orElse(null);
    }
}
