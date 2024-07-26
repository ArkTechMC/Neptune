package com.iafenvoy.neptune.object.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.Arrays;
import java.util.function.Supplier;

public class ToolMaterialUtil {
    @SafeVarargs
    public static ToolMaterial of(int uses, float speed, float attackDamageBonus, int level, int enchantmentLevel, Supplier<ItemConvertible>... repairIngredients) {
        return new ToolMaterial() {
            @Override
            public int getDurability() {
                return uses;
            }

            @Override
            public float getMiningSpeedMultiplier() {
                return speed;
            }

            @Override
            public float getAttackDamage() {
                return attackDamageBonus;
            }

            @Override
            public int getMiningLevel() {
                return level;
            }

            @Override
            public int getEnchantability() {
                return enchantmentLevel;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.ofItems(Arrays.stream(repairIngredients).map(Supplier::get).toArray(ItemConvertible[]::new));
            }
        };
    }
}
