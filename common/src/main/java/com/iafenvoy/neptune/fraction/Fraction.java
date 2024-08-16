package com.iafenvoy.neptune.fraction;

import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;

import java.util.HashMap;
import java.util.Map;

public record Fraction(String name, ItemStack banner, Consumer2<PacketByteBuf, PlayerEntity> abilityHandler) {
    private static final Map<String, Fraction> BY_NAME = new HashMap<>();
    public static final Fraction NONE = new Fraction("none", new ItemStack(Items.WHITE_BANNER), (buf, player) -> {
    });

    public Fraction(String name, ItemStack banner, Consumer2<PacketByteBuf, PlayerEntity> abilityHandler) {
        this.name = name;
        this.banner = banner;
        this.abilityHandler = abilityHandler;
        BY_NAME.put(name, this);
    }

    public static Fraction getByName(String name) {
        return BY_NAME.getOrDefault(name, NONE);
    }
}
