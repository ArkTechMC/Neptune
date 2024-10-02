package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.data.NeptunePlayerData;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
    private void handleCustomSkin(CallbackInfoReturnable<Identifier> cir) {
        NeptunePlayerData data = NeptunePlayerData.byPlayer(this);
        if (data.getUsingTexture() != null && MinecraftClient.getInstance().getTextureManager().getOrDefault(data.getUsingTexture(), MissingSprite.getMissingSpriteTexture()) != MissingSprite.getMissingSpriteTexture())
            cir.setReturnValue(data.getUsingTexture());
    }
}
