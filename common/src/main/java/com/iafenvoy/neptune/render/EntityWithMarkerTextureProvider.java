package com.iafenvoy.neptune.render;

import net.minecraft.util.Identifier;

import java.util.Optional;

public interface EntityWithMarkerTextureProvider extends EntityTextureProvider {
    Optional<Identifier> getMarkerTextureId();
}
