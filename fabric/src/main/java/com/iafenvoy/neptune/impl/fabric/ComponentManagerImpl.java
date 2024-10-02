package com.iafenvoy.neptune.impl.fabric;

import com.iafenvoy.neptune.fabric.component.NeptunePlayerComponent;
import com.iafenvoy.neptune.data.NeptunePlayerData;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static NeptunePlayerData getPlayerData(LivingEntity entity) {
        Optional<NeptunePlayerComponent> data = NeptunePlayerComponent.NEPTUNE_PLAYER_COMPONENT.maybeGet(entity);
        return data.map(NeptunePlayerComponent::getData).orElse(null);
    }
}
