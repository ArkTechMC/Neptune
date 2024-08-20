package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.screen.gui.WeaponDeskScreen;
import com.iafenvoy.neptune.screen.handler.WeaponDeskScreenHandler;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class NeptuneScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.SCREEN_HANDLER);

    public static final RegistrySupplier<ScreenHandlerType<WeaponDeskScreenHandler>> WEAPON_DESK = REGISTRY.register("weapon_desk", () -> new ScreenHandlerType<>(WeaponDeskScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));

    public static void initClient() {
        MenuRegistry.registerScreenFactory(WEAPON_DESK.get(), WeaponDeskScreen::new);
    }
}
