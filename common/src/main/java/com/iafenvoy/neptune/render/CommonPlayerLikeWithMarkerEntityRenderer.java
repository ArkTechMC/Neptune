package com.iafenvoy.neptune.render;

import com.iafenvoy.neptune.render.feature.MarkerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.MobEntity;

@Environment(EnvType.CLIENT)
public class CommonPlayerLikeWithMarkerEntityRenderer<T extends MobEntity & EntityWithMarkerTextureProvider> extends CommonPlayerLikeEntityRenderer<T> {
    public CommonPlayerLikeWithMarkerEntityRenderer(EntityRendererFactory.Context ctx) {
        this(ctx, true);
    }

    public CommonPlayerLikeWithMarkerEntityRenderer(EntityRendererFactory.Context ctx, boolean glint) {
        this(ctx, glint, false);
    }

    public CommonPlayerLikeWithMarkerEntityRenderer(EntityRendererFactory.Context ctx, boolean glint, boolean slim) {
        super(ctx, slim);
        this.addFeature(new MarkerFeatureRenderer<>(this, glint));
        this.model.setVisible(true);
    }
}
