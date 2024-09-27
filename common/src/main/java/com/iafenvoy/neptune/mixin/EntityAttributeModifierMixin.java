package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.object.item.ItemBase;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EntityAttributeModifier.class)
public class EntityAttributeModifierMixin {
    @Inject(method = "getId", at = @At("TAIL"), cancellable = true)
    private void warpToCorrect(CallbackInfoReturnable<UUID> cir) {
        cir.setReturnValue(ItemBase.processUuid(cir.getReturnValue()));
    }
}
