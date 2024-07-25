package com.iafenvoy.neptune.mixin;

import com.iafenvoy.neptune.ServerHelper;
import com.iafenvoy.neptune.util.Timeout;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onServerCreated(CallbackInfo ci) {
        ServerHelper.server = (MinecraftServer) (Object) this;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void endTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Timeout.runTimeout((MinecraftServer) (Object) this);
    }
}
