package com.iafenvoy.neptune.object.entity;

import net.minecraft.nbt.NbtCompound;

/**
 * @author Alexthe666
 * @since 1.7.0
 */
public interface INeptuneDataEntity {
    NbtCompound getNeptuneEntityData();

    void setNeptuneEntityData(NbtCompound nbt);
}
