package com.iafenvoy.neptune.fraction;

import com.iafenvoy.neptune.impl.ComponentManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class FractionData {
    private Fraction fraction;
    private boolean abilityEnabled;

    public FractionData() {
        this.fraction = Fraction.NONE;
        this.abilityEnabled = false;
    }

    public void encode(NbtCompound tag) {
        tag.putString("fraction", this.fraction.id().toString());
        tag.putBoolean("abilityEnabled", this.abilityEnabled);
    }

    public void decode(NbtCompound tag) {
        this.fraction = Fraction.getById(tag.getString("fraction"));
        this.abilityEnabled = tag.getBoolean("abilityEnabled");
    }

    public Fraction getFraction() {
        return fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public boolean isAbilityEnabled() {
        return abilityEnabled;
    }

    public void setAbilityEnabled(boolean abilityEnabled) {
        this.abilityEnabled = abilityEnabled;
    }

    public static FractionData byPlayer(PlayerEntity player) {
        return ComponentManager.getFractionData(player);
    }
}
