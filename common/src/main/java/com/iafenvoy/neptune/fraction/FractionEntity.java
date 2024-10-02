package com.iafenvoy.neptune.fraction;

import com.iafenvoy.neptune.impl.ComponentManager;
import com.iafenvoy.neptune.mixin.MobEntityAccessor;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface FractionEntity {
    Fraction getFraction();

    static <T extends MobEntity & FractionEntity> void addTarget(T entity) {
        GoalSelector goalSelector = ((MobEntityAccessor) entity).getTargetSelector();
        goalSelector.add(1, new ActiveTargetGoal<>(entity, MobEntity.class, true, mob -> {
            if (mob instanceof FractionEntity mobF)
                return mobF.getFraction() != entity.getFraction();
            return false;
        }));
        goalSelector.add(1, new ActiveTargetGoal<>(entity, PlayerEntity.class, true, mob -> {
            if (mob instanceof PlayerEntity player)
                return ComponentManager.getPlayerData(player).getFraction() != entity.getFraction();
            return false;
        }));
    }
}
