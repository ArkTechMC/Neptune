package com.iafenvoy.neptune.object.entity;

import com.iafenvoy.neptune.fraction.Fraction;
import com.iafenvoy.neptune.fraction.FractionEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class MonsterFractionEntityBase extends MonsterEntityBase implements FractionEntity {
    private final Fraction fraction;

    protected MonsterFractionEntityBase(EntityType<? extends HostileEntity> entityType, World world, EntityGroup mobType, Fraction fraction) {
        super(entityType, world, mobType);
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
