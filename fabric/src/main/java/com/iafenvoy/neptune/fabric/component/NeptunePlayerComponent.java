package com.iafenvoy.neptune.fabric.component;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.data.NeptunePlayerData;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class NeptunePlayerComponent implements ComponentV3, AutoSyncedComponent {
    public static final ComponentKey<NeptunePlayerComponent> NEPTUNE_PLAYER_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Neptune.MOD_ID, "player_data"), NeptunePlayerComponent.class);

    private final LivingEntity entity;
    private final NeptunePlayerData data;

    public NeptunePlayerComponent(LivingEntity entity) {
        this.entity = entity;
        this.data = new NeptunePlayerData(() -> NEPTUNE_PLAYER_COMPONENT.sync(this.entity));
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public NeptunePlayerData getData() {
        return this.data;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
        this.data.decode(tag);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        this.data.encode(tag);
    }
}
