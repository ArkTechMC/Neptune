package com.iafenvoy.neptune.screen.handler;

import com.google.common.collect.Lists;
import com.iafenvoy.neptune.data.recipe.WeaponDeskRecipe;
import com.iafenvoy.neptune.registry.NeptuneBlocks;
import com.iafenvoy.neptune.registry.NeptuneScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class WeaponDeskScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Property selectedRecipe;
    private final World world;
    private List<WeaponDeskRecipe> availableRecipes;
    long lastTakeTime;
    private final Slot materialSlot, stickSlot, outputSlot;
    Runnable contentsChangedListener;
    private final Inventory input;
    private final CraftingResultInventory output;

    public WeaponDeskScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public WeaponDeskScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(NeptuneScreenHandlers.WEAPON_DESK.get(), syncId);
        this.selectedRecipe = Property.create();
        this.availableRecipes = Lists.newArrayList();
        this.contentsChangedListener = () -> {
        };
        this.input = new SimpleInventory(2) {
            public void markDirty() {
                super.markDirty();
                WeaponDeskScreenHandler.this.onContentChanged(this);
                WeaponDeskScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new CraftingResultInventory();
        this.context = context;
        this.world = playerInventory.player.getWorld();
        this.materialSlot = this.addSlot(new Slot(this.input, 0, 12, 23));
        this.stickSlot = this.addSlot(new Slot(this.input, 1, 12, 43));
        this.outputSlot = this.addSlot(new Slot(this.output, 2, 143, 33) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.getWorld(), player, stack.getCount());
                Recipe<?> recipe = WeaponDeskScreenHandler.this.output.getLastRecipe();
                if (!(recipe instanceof WeaponDeskRecipe weaponDeskRecipe)) return;
                ItemStack itemStack = WeaponDeskScreenHandler.this.materialSlot.takeStack(weaponDeskRecipe.materialCount());
                WeaponDeskScreenHandler.this.stickSlot.takeStack(weaponDeskRecipe.stickCount());
                if (!itemStack.isEmpty())
                    WeaponDeskScreenHandler.this.populateResult();
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (WeaponDeskScreenHandler.this.lastTakeTime != l) {
                        world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        WeaponDeskScreenHandler.this.lastTakeTime = l;
                    }

                });
                super.onTakeItem(player, stack);
            }
        });

        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addProperty(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<WeaponDeskRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public boolean canCraft() {
        return this.materialSlot.hasStack() && this.stickSlot.hasStack() && !this.availableRecipes.isEmpty();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, NeptuneBlocks.WEAPON_DESK.get());
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.availableRecipes.size();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.updateInput(inventory);
    }

    private void updateInput(Inventory input) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        if (this.materialSlot.hasStack() && this.stickSlot.hasStack())
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(WeaponDeskRecipe.Type.INSTANCE, input, this.world);
    }

    private void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            WeaponDeskRecipe stonecuttingRecipe = this.availableRecipes.get(this.selectedRecipe.get());
            ItemStack itemStack = stonecuttingRecipe.craft(this.input, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.output.setLastRecipe(stonecuttingRecipe);
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        }

        this.sendContentUpdates();
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return NeptuneScreenHandlers.WEAPON_DESK.get();
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (slot == 1) {
                item.onCraft(itemStack2, player.getWorld(), player);
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot == 0) {
                if (!this.insertItem(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.world.getRecipeManager().getFirstMatch(RecipeType.STONECUTTING, new SimpleInventory(itemStack2), this.world).isPresent()) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 2 && slot < 29) {
                if (!this.insertItem(itemStack2, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 29 && slot < 38 && !this.insertItem(itemStack2, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            }

            slot2.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }

        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.output.removeStack(1);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }
}