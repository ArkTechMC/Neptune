package com.iafenvoy.neptune.object;

import net.minecraft.util.Identifier;

public class IdUtil {
    public static String build(String namespace, String path) {
        return new Identifier(namespace, path).toString();
    }
}
