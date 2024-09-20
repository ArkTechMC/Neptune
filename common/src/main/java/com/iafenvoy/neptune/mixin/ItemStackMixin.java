package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.object.item.ItemBase;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;"))
    private UUID warpToCorrect(EntityAttributeModifier instance) {
        return ItemBase.processUuid(instance.getId());
    }
}
