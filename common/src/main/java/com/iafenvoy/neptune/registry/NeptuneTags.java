package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class NeptuneTags {
    public static final TagKey<Item> RENDER_BIG_WEAPON = create("render/big_weapon");
    public static final TagKey<Item> RENDER_REVERSE_WEAPON = create("render/reverse_weapon");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Neptune.MOD_ID, id));
    }
}
