package com.iafenvoy.neptune.compat.emi;

import com.iafenvoy.neptune.Neptune;
import com.iafenvoy.neptune.data.recipe.WeaponDeskRecipe;
import com.iafenvoy.neptune.registry.NeptuneBlocks;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EmiEntrypoint
public class WeaponDeskRecipePlugin implements EmiPlugin {
    private static final EmiTexture TEXTURE = new EmiTexture(Identifier.of(Neptune.MOD_ID, "textures/gui/gui_boss_spawn_recipe.png"), 0, 0, 140, 44);
    private static final EmiStack WORKSTATION = EmiStack.of(NeptuneBlocks.WEAPON_DESK.get());
    private static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(WeaponDeskRecipe.ID, WORKSTATION, TEXTURE);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.addWorkstation(CATEGORY, WORKSTATION);
        for (WeaponDeskRecipe recipe : registry.getRecipeManager().listAllOfType(WeaponDeskRecipe.Type.INSTANCE))
            registry.addRecipe(new EmiWeaponDeskRecipe(recipe));
    }

    private record EmiWeaponDeskRecipe(WeaponDeskRecipe holder) implements EmiRecipe {
        @Override
        public EmiRecipeCategory getCategory() {
            return CATEGORY;
        }

        @Override
        public @Nullable Identifier getId() {
            return this.holder.id();
        }

        @Override
        public List<EmiIngredient> getInputs() {
            return List.of(EmiIngredient.of(this.holder.material(), this.holder.materialCount()), EmiIngredient.of(this.holder.stick(), this.holder.stickCount()));
        }

        @Override
        public List<EmiStack> getOutputs() {
            return List.of(EmiStack.of(this.holder.result().copy()));
        }

        @Override
        public int getDisplayWidth() {
            return 96;
        }

        @Override
        public int getDisplayHeight() {
            return 18;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            List<EmiIngredient> ingredients = this.getInputs();
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 46, 1);
            widgets.addSlot(ingredients.get(0), 0, 0);
            widgets.addSlot(ingredients.get(1), 20, 0);
            widgets.addSlot(this.getOutputs().get(0), 78, 0).recipeContext(this);
        }
    }
}
