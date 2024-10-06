package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.render.SkullRenderRegistry;
import com.iafenvoy.neptune.render.tool.BackToolRenderer;
import com.iafenvoy.neptune.render.tool.BeltToolRenderer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new BackToolRenderer(this, ctx.getHeldItemRenderer()));
        this.addFeature(new BeltToolRenderer(this, ctx.getHeldItemRenderer()));
    }

    @Inject(method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    private void handlePlayerTexture(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        ItemStack head = abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (head.getItem() instanceof SkullItem skullItem && skullItem.getBlock() instanceof AbstractSkullBlock skullBlock) {
            Identifier texture = SkullRenderRegistry.getTextureFromType(skullBlock.getSkullType());
            if (texture != null) {
                cir.setReturnValue(texture);
                return;
            }
            if (skullBlock.getSkullType() == SkullBlock.Type.PLAYER) {
                GameProfile gameProfile = null;
                if (head.getNbt() != null) {//Copy from net.minecraft.block.PlayerSkullBlock#onPlaced
                    NbtCompound nbtCompound = head.getNbt();
                    if (nbtCompound.contains("SkullOwner", 10))
                        gameProfile = NbtHelper.toGameProfile(nbtCompound.getCompound("SkullOwner"));
                    else if (nbtCompound.contains("SkullOwner", 8) && !Util.isBlank(nbtCompound.getString("SkullOwner")))
                        gameProfile = new GameProfile(null, nbtCompound.getString("SkullOwner"));
                }
                if (gameProfile != null) {
                    PlayerSkinProvider skinProvider = MinecraftClient.getInstance().getSkinProvider();
                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = skinProvider.getTextures(gameProfile);
                    if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
                        cir.setReturnValue(skinProvider.loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
                }
            }
        }
    }
}
