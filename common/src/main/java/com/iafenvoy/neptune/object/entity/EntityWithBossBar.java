package com.iafenvoy.neptune.object.entity;

import com.iafenvoy.neptune.render.BossBarRenderHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class EntityWithBossBar extends MonsterEntityBase {
    public final ServerBossBar bossBar;

    protected EntityWithBossBar(EntityType<? extends HostileEntity> entityType, World world, EntityGroup mobType, BossBar.Color barColor) {
        super(entityType, world, mobType);
        this.bossBar = new ServerBossBar(this.getDisplayName(), barColor, BossBar.Style.PROGRESS);
        BossBarRenderHelper.addBossBar(this.getClass(), this.bossBar.getUuid());
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        BossBarRenderHelper.removeBossBar(this.getClass(), this.bossBar.getUuid());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }
}
