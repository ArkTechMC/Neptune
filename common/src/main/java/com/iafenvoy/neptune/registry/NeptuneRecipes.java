package com.iafenvoy.neptune.registry;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.data.recipe.WeaponDeskRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryKeys;

public class NeptuneRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.RECIPE_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(Neptune.MOD_ID, RegistryKeys.RECIPE_SERIALIZER);

    static {
        TYPE_REGISTRY.register(WeaponDeskRecipe.ID, () -> WeaponDeskRecipe.Type.INSTANCE);
        SERIALIZER_REGISTRY.register(WeaponDeskRecipe.ID, () -> WeaponDeskRecipe.Serializer.INSTANCE);
    }
}
