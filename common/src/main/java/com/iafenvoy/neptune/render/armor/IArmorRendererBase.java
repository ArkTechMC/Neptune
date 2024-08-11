package com.iafenvoy.neptune.render.armor;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;

public interface IArmorRendererBase<T extends LivingEntity> {
    HashMap<ItemConvertible, IArmorRendererBase<?>> RENDERERS = new HashMap<>();

    BipedEntityModel<T> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, BipedEntityModel<T> defaultModel);

    Identifier getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot);

    static void register(IArmorRendererBase<?> renderer, ItemConvertible... items) {
        Arrays.stream(items).forEach(x -> RENDERERS.put(x, renderer));
    }
}
