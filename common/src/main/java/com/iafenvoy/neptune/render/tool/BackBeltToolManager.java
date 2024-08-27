package com.iafenvoy.neptune.render.tool;

import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BackBeltToolManager {
    protected static final String BACK = "back";
    protected static final String BELT = "belt";
    private static final Map<Item, BackHolder> BACK_HOLDERS = new HashMap<>();
    private static final Map<Item, BeltHolder> BELT_HOLDERS = new HashMap<>();

    @ExpectPlatform
    public static Map<Place, ItemStack> getAllEquipped(PlayerEntity player) {
        throw new AssertionError("This method should be replaced by Architectury");
    }

    @Nullable
    public static BackHolder getBack(Item item) {
        return BACK_HOLDERS.get(item);
    }

    @Nullable
    public static BeltHolder getBelt(Item item) {
        return BELT_HOLDERS.get(item);
    }

    public static ItemStack getEquipped(PlayerEntity player, Place place) {
        return getAllEquipped(player).getOrDefault(place, ItemStack.EMPTY);
    }

    public static void registerBack(Item item, boolean alone, Consumer2<MatrixStack, Boolean> transformer) {
        BACK_HOLDERS.put(item, new BackHolder(item, alone, transformer));
    }

    public static void registerBack(Item item, Consumer2<MatrixStack, Boolean> transformer) {
        registerBack(item, false, transformer);
    }

    public static void registerBack(boolean alone, Consumer2<MatrixStack, Boolean> transformer, Item... items) {
        for (Item item : items) registerBack(item, alone, transformer);
    }

    public static void registerBack(Consumer2<MatrixStack, Boolean> transformer, Item... items) {
        for (Item item : items) registerBack(item, transformer);
    }

    public static void registerBelt(Item item, Consumer2<MatrixStack, Boolean> transformer) {
        BELT_HOLDERS.put(item, new BeltHolder(item, transformer));
    }

    public static void registerBelt(Consumer2<MatrixStack, Boolean> transformer, Item... items) {
        for (Item item : items) registerBelt(item, transformer);
    }

    public record BackHolder(Item item, boolean alone, Consumer2<MatrixStack, Boolean> transformer) {

    }

    public record BeltHolder(Item item, Consumer2<MatrixStack, Boolean> transformer) {

    }

    public enum Place {
        BACK_LEFT, BACK_RIGHT, BELT_LEFT, BELT_RIGHT
    }
}
