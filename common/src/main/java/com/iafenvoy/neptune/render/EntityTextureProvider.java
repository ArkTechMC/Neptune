package com.iafenvoy.neptune.render;

import net.minecraft.util.Identifier;

public interface EntityTextureProvider {
    Identifier getTextureId();

    default float getScale() {
        return 1;
    }
}
