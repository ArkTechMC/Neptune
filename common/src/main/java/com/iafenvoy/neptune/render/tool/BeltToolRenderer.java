package com.iafenvoy.neptune.render.tool;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class BeltToolRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private final HeldItemRenderer heldItemRenderer;

    public BeltToolRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider provider, int i, AbstractClientPlayerEntity entity, float f, float g, float h, float j, float k, float l) {
        Map<BackBeltToolManager.Place, ItemStack> stacks = BackBeltToolManager.getAllEquipped(entity);
        if (stacks.containsKey(BackBeltToolManager.Place.BELT_LEFT))
            this.renderItem(stacks.get(BackBeltToolManager.Place.BELT_LEFT), matrices, provider, i, entity, true);
        if (stacks.containsKey(BackBeltToolManager.Place.BELT_RIGHT))
            this.renderItem(stacks.get(BackBeltToolManager.Place.BELT_RIGHT), matrices, provider, i, entity, false);
    }

    private void renderItem(ItemStack stack, MatrixStack matrices, VertexConsumerProvider provider, int i, AbstractClientPlayerEntity entity, boolean left) {
        matrices.push();
        ModelPart modelPart = this.getContextModel().body;
        modelPart.rotate(matrices);
        double switchBeltSide = 0.29D;
        matrices.translate(switchBeltSide * (left ? 1 : -1), 0.5D, 0.05D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
        matrices.scale(1.5f, 1.5f, 1.5f);
        BackBeltToolManager.BeltHolder holder = BackBeltToolManager.getBelt(stack.getItem());
        if (holder != null) holder.transformer().accept(matrices,left);
        this.heldItemRenderer.renderItem(entity, stack, ModelTransformationMode.GROUND, left, matrices, provider, i);
        matrices.pop();
    }
}