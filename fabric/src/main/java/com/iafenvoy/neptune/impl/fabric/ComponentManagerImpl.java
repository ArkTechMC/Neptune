package com.iafenvoy.neptune.impl.fabric;

import com.iafenvoy.neptune.fabric.component.FractionComponent;
import com.iafenvoy.neptune.fraction.FractionData;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static FractionData getFractionData(LivingEntity entity) {
        Optional<FractionComponent> data = FractionComponent.FRACTION_COMPONENT.maybeGet(entity);
        return data.map(FractionComponent::getData).orElse(null);
    }
}
