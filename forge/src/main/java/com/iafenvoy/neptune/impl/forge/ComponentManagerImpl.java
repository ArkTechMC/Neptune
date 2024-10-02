package com.iafenvoy.neptune.impl.forge;

import com.iafenvoy.neptune.forge.component.NeptunePlayerDataProvider;
import com.iafenvoy.neptune.forge.component.NeptunePlayerDataStorage;
import com.iafenvoy.neptune.data.NeptunePlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static NeptunePlayerData getPlayerData(LivingEntity entity) {
        LazyOptional<NeptunePlayerDataStorage> storageLazyOptional = entity.getCapability(NeptunePlayerDataProvider.CAPABILITY);
        if (!storageLazyOptional.isPresent()) return null;
        Optional<NeptunePlayerDataStorage> storage = storageLazyOptional.resolve();
        return storage.map(NeptunePlayerDataStorage::getPlayerData).orElse(null);
    }
}
