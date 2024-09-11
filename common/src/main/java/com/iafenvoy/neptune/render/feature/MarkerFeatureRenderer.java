package com.iafenvoy.neptune.render.feature;

import com.iafenvoy.neptune.render.EntityWithMarkerTextureProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MarkerFeatureRenderer<T extends MobEntity & EntityWithMarkerTextureProvider> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    private final boolean glint;

    public MarkerFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context, boolean glint) {
        super(context);
        this.glint = glint;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        Optional<Identifier> marker = entity.getMarkerTextureId();
        BipedEntityModel<T> model = this.getContextModel();
        marker.ifPresent(id -> model.render(matrices, vertexConsumers.getBuffer(this.glint ? RenderLayer.getEntityTranslucentEmissive(id) : RenderLayer.getEntityCutout(id)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1));
    }
}
