package com.iafenvoy.neptune.render.glint;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

@Environment(EnvType.CLIENT)
public class GlintLayerManager {
    private static final Map<GlintManager.GlintHolder, RenderLayer> LAYERS = new HashMap<>();

    @ApiStatus.Internal
    public static void registerAll(SortedMap<RenderLayer, BufferBuilder> map) {
        for (GlintManager.GlintHolder holder : GlintManager.HOLDERS) {
            if (holder.texture() == null) continue;
            RenderLayer layer = RenderLayer.of("glint_" + holder.id(),
                    VertexFormats.POSITION_TEXTURE,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.DIRECT_GLINT_PROGRAM)
                            .texture(new RenderPhase.Texture(holder.texture(), true, false))
                            .cull(RenderPhase.DISABLE_CULLING)
                            .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                            .transparency(RenderPhase.GLINT_TRANSPARENCY)
                            .texturing(RenderPhase.GLINT_TEXTURING)
                            .build(false));
            map.put(layer, new BufferBuilder(layer.getExpectedBufferSize()));
            LAYERS.put(holder, layer);
        }
    }

    public static VertexConsumer getConsumerById(VertexConsumerProvider provider, RenderLayer layer, boolean glint, String id) {
        if (glint)
            return VertexConsumers.union(provider.getBuffer(LAYERS.getOrDefault(GlintManager.BY_ID.getOrDefault(id, GlintManager.DEFAULT), RenderLayer.getDirectGlint())), provider.getBuffer(layer));
        else return provider.getBuffer(layer);
    }

    public static VertexConsumer getConsumerByStack(VertexConsumerProvider provider, RenderLayer layer, ItemStack stack, boolean glint) {
        for (GlintManager.GlintHolder holder : GlintManager.HOLDERS)
            if (holder.match(stack))
                return VertexConsumers.union(provider.getBuffer(LAYERS.getOrDefault(holder, RenderLayer.getDirectGlint())), provider.getBuffer(layer));
        if (glint)
            return VertexConsumers.union(provider.getBuffer(RenderLayer.getDirectGlint()), provider.getBuffer(layer));
        return provider.getBuffer(layer);
    }
}
