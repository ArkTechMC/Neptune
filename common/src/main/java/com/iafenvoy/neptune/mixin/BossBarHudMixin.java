package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.render.BossBarRenderHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.entity.boss.BossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {
    @Inject(method = "renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;II)V", at = @At("HEAD"), cancellable = true)
    private void onRenderHud(DrawContext context, int x, int y, BossBar bossBar, int width, int height, CallbackInfo ci) {
        if (BossBarRenderHelper.render(context, x, y, bossBar, width, height))
            ci.cancel();
    }
}
