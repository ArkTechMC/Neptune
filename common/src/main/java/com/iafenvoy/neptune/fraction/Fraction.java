package com.iafenvoy.neptune.fraction;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record Fraction(Identifier id, Supplier<ItemStack> banner,
                       Consumer2<PacketByteBuf, PlayerEntity> abilityHandler) {
    private static final List<Fraction> VALUES = new ArrayList<>();
    private static final Map<Identifier, Fraction> BY_NAME = new HashMap<>();
    public static final Fraction NONE = new Fraction(new Identifier(Neptune.MOD_ID, "none"), () -> new ItemStack(Items.WHITE_BANNER), (buf, player) -> {
    });

    public Fraction(Identifier id, Supplier<ItemStack> banner, Consumer2<PacketByteBuf, PlayerEntity> abilityHandler) {
        this.id = id;
        this.banner = banner;
        this.abilityHandler = abilityHandler;
        VALUES.add(this);
        BY_NAME.put(id, this);
    }

    public static List<Fraction> values() {
        return VALUES;
    }

    public static Fraction getById(Identifier name) {
        return BY_NAME.getOrDefault(name, NONE);
    }

    public static Fraction getById(String name) {
        return getById(new Identifier(name));
    }
}
