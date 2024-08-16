package com.iafenvoy.neptune.object.entity;

import com.iafenvoy.neptune.fraction.Fraction;
import com.iafenvoy.neptune.fraction.FractionEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class FractionEntityWithBossBar extends EntityWithBossBar implements FractionEntity {
    private final Fraction fraction;

    protected FractionEntityWithBossBar(EntityType<? extends HostileEntity> entityType, World world, EntityGroup mobType, BossBar.Color barColor, Fraction fraction) {
        super(entityType, world, mobType, barColor);
        this.fraction = fraction;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        FractionEntity.addTarget(this);
    }

    @Override
    public Fraction getFraction() {
        return this.fraction;
    }
}
