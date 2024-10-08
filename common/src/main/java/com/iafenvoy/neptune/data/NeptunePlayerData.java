package com.iafenvoy.neptune.data;

import com.iafenvoy.neptune.fraction.Fraction;
import com.iafenvoy.neptune.impl.ComponentManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class NeptunePlayerData {
    private final Runnable sync;
    private Fraction fraction;
    private boolean abilityEnabled;
    @Nullable
    @Deprecated
    private Identifier usingTexture = null;

    public NeptunePlayerData(Runnable sync) {
        this.sync = sync;
        this.fraction = Fraction.NONE;
        this.abilityEnabled = false;
    }

    public void encode(NbtCompound tag) {
        tag.putString("fraction", this.fraction.id().toString());
        tag.putBoolean("abilityEnabled", this.abilityEnabled);
        if (this.usingTexture != null) tag.putString("usingTexture", this.usingTexture.toString());
    }

    public void decode(NbtCompound tag) {
        this.fraction = Fraction.getById(tag.getString("fraction"));
        this.abilityEnabled = tag.getBoolean("abilityEnabled");
        this.usingTexture = tag.contains("usingTexture") ? new Identifier(tag.getString("usingTexture")) : null;
    }

    public Fraction getFraction() {
        return this.fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
        this.sync.run();
    }

    public boolean isAbilityEnabled() {
        return this.abilityEnabled;
    }

    public void setAbilityEnabled(boolean abilityEnabled) {
        this.abilityEnabled = abilityEnabled;
        this.sync.run();
    }

    public static NeptunePlayerData byPlayer(PlayerEntity player) {
        return ComponentManager.getPlayerData(player);
    }
}
