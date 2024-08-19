package com.iafenvoy.neptune.object.entity;

import com.iafenvoy.neptune.render.Stage;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public class StagedMonsterEntityBase extends MonsterEntityBase implements Stage.StagedEntity {
    private Stage stage;

    protected StagedMonsterEntityBase(EntityType<? extends HostileEntity> entityType, World world, EntityGroup mobType, Stage stage) {
        super(entityType, world, mobType);
        this.stage = stage;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("stage"))
            this.stage = Stage.getByIndex(nbt.getInt("stage"));
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("stage", this.stage.getIndex());
        return nbt;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.stage.ordinal());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.stage = Stage.values()[packet.getEntityData()];
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }
}
