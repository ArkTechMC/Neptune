package com.iafenvoy.neptune.forge.component;

import com.iafenvoy.neptune.fraction.FractionData;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class FractionDataStorage implements INBTSerializable<NbtCompound> {
    private final FractionData playerData = new FractionData();

    public FractionDataStorage() {
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

    public FractionData getPlayerData() {
        return this.playerData;
    }
}
