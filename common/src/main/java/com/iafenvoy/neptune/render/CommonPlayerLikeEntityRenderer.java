package com.iafenvoy.neptune.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CommonPlayerLikeEntityRenderer<T extends MobEntity & EntityTextureProvider> extends BipedEntityRenderer<T, BipedEntityModel<T>> {
    public CommonPlayerLikeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.getTextureId();
    }
}
