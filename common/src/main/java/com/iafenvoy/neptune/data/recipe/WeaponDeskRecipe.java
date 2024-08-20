package com.iafenvoy.neptune.data.recipe;

import com.google.gson.JsonObject;
import com.iafenvoy.neptune.Neptune;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public record WeaponDeskRecipe(Identifier id, Ingredient material, int materialCount, Ingredient stick, int stickCount,
                               ItemStack result) implements Recipe<Inventory> {
    public static final Identifier ID = new Identifier(Neptune.MOD_ID, "weapon_desk");

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 2) return false;
        ItemStack materialStack = inventory.getStack(0), stickStack = inventory.getStack(1);
        return this.material.test(materialStack)
                && materialStack.getCount() >= this.materialCount
                && this.stick.test(stickStack)
                && stickStack.getCount() >= this.stickCount;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        if (inventory.size() < 2) return ItemStack.EMPTY;
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.result.copy();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public enum Type implements RecipeType<WeaponDeskRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<WeaponDeskRecipe> {
        INSTANCE;

        @Override
        public WeaponDeskRecipe read(Identifier id, JsonObject json) {
            JsonObject material = json.get("material").getAsJsonObject(), stick = json.get("stick").getAsJsonObject(), result = json.get("result").getAsJsonObject();
            return new WeaponDeskRecipe(id,
                    Ingredient.fromJson(material, false),
                    JsonHelper.getInt(material, "count", 1),
                    Ingredient.fromJson(stick, false),
                    JsonHelper.getInt(stick, "count", 1),
                    ShapedRecipe.outputFromJson(result));
        }

        @Override
        public WeaponDeskRecipe read(Identifier id, PacketByteBuf buf) {
            return new WeaponDeskRecipe(id,
                    Ingredient.fromPacket(buf),
                    buf.readInt(),
                    Ingredient.fromPacket(buf),
                    buf.readInt(),
                    ItemStack.fromNbt(buf.readNbt()));
        }

        @Override
        public void write(PacketByteBuf buf, WeaponDeskRecipe recipe) {
            recipe.material.write(buf);
            buf.writeInt(recipe.materialCount);
            recipe.stick.write(buf);
            buf.writeInt(recipe.stickCount);
            NbtCompound compound = new NbtCompound();
            recipe.result.writeNbt(compound);
            buf.writeNbt(compound);
        }
    }
}
