package com.iafenvoy.neptune.item.block;

import com.iafenvoy.neptune.item.block.entity.WeaponDeskBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WeaponDeskBlock extends BlockWithEntity {
    public WeaponDeskBlock() {
        super(Settings.create().strength(2.5F).sounds(BlockSoundGroup.STONE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WeaponDeskBlockEntity weaponDesk) {
            player.openHandledScreen(weaponDesk);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WeaponDeskBlockEntity(pos, state);
    }
}
