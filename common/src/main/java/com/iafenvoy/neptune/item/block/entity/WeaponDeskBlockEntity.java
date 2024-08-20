package com.iafenvoy.neptune.item.block.entity;

import com.iafenvoy.neptune.registry.NeptuneBlockEntities;
import com.iafenvoy.neptune.screen.handler.WeaponDeskScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;

public class WeaponDeskBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public WeaponDeskBlockEntity(BlockPos pos, BlockState state) {
        super(NeptuneBlockEntities.WEAPON_DESK.get(), pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.neptune.weapon_desk.title");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WeaponDeskScreenHandler(syncId, playerInventory, new ScreenHandlerContext() {
            @Override
            public <T> Optional<T> get(BiFunction<World, BlockPos, T> getter) {
                return Optional.of(getter.apply(WeaponDeskBlockEntity.this.world, WeaponDeskBlockEntity.this.pos));
            }
        });
    }
}
