package com.iafenvoy.neptune.object.entity;

import com.iafenvoy.neptune.render.Stage;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class StagedMonsterEntityBase extends MonsterEntityBase implements Stage.StagedEntity {
    private static final TrackedData<Integer> STAGE = DataTracker.registerData(StagedMonsterEntityBase.class, TrackedDataHandlerRegistry.INTEGER);
    private final Stage stage;

    protected StagedMonsterEntityBase(EntityType<? extends HostileEntity> entityType, World world, EntityGroup mobType, Stage stage) {
        super(entityType, world, mobType);
        this.stage = stage;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STAGE, this.stage.getIndex());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("stage"))
            this.setStage(nbt.getInt("stage"));
        else this.setStage(this.stage);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("stage", this.getStageIndex());
        return nbt;
    }

    public void setStage(Stage stage) {
        this.setStage(stage.getIndex());
    }

    public void setStage(int stage) {
        this.dataTracker.set(STAGE, stage, true);
    }

    @Override
    public Stage getStage() {
        return Stage.getByIndex(this.getStageIndex());
    }

    public int getStageIndex() {
        return this.dataTracker.get(STAGE);
    }
}
