package com.iafenvoy.neptune.forge.component;

import com.iafenvoy.neptune.data.NeptunePlayerData;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class NeptunePlayerDataStorage implements ITickableCapability {
    private final NeptunePlayerData playerData = new NeptunePlayerData(() -> {
    });

    public NeptunePlayerDataStorage() {
    }

    @Override
    public NbtCompound serializeNBT() {
        NbtCompound compound = new NbtCompound();
        this.playerData.encode(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(NbtCompound compound) {
        this.playerData.decode(compound);
    }

    public NeptunePlayerData getPlayerData() {
        return this.playerData;
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean isDirty() {
        return false;
    }
}
