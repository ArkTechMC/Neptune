package com.iafenvoy.neptune.fabric.component;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.fraction.FractionData;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class FractionComponent implements ComponentV3, AutoSyncedComponent {
    public static final ComponentKey<FractionComponent> FRACTION_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier.of(Neptune.MOD_ID, "fraction"), FractionComponent.class);

    private final LivingEntity entity;
    private final FractionData data;

    public FractionComponent(LivingEntity entity) {
        this.entity = entity;
        this.data = new FractionData();
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public FractionData getData() {
        return this.data;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.data.decode(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        this.data.encode(tag);
    }
}
