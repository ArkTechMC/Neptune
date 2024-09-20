package com.iafenvoy.neptune.object.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Function;

public class ItemBase extends Item implements ISwingable {
    public ItemBase(Function<Settings, Settings> properties) {
        super(properties.apply(new Settings().rarity(Rarity.COMMON).maxCount(64)));
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, PlayerEntity entity) {
        return false;
    }

    public boolean onEntitySwing(ItemStack itemtack, Entity entity) {
        return this.onSwingHand(itemtack, entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
    }

    public boolean onSwingHand(ItemStack itemtack, World world, double x, double y, double z) {
        return false;
    }

    public static UUID processUuid(UUID origin) {
        if (origin.equals(ATTACK_DAMAGE_MODIFIER_ID)) return ATTACK_DAMAGE_MODIFIER_ID;
        if (origin.equals(ATTACK_SPEED_MODIFIER_ID)) return ATTACK_SPEED_MODIFIER_ID;
        return origin;
    }
}
