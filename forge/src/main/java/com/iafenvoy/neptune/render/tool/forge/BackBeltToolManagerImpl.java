package com.iafenvoy.neptune.render.tool.forge;

import com.iafenvoy.neptune.render.tool.BackBeltToolManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class BackBeltToolManagerImpl extends BackBeltToolManager {
    public static Map<BackBeltToolManager.Place, ItemStack> getAllEquipped(PlayerEntity player) {
        Optional<ICuriosItemHandler> lazyOptional = CuriosApi.getCuriosInventory(player).resolve();
        Map<BackBeltToolManager.Place, ItemStack> map = new HashMap<>();
        if (lazyOptional.isEmpty()) return map;
        ICuriosItemHandler handler = lazyOptional.get();
        Map<String, ICurioStacksHandler> all = handler.getCurios();
        IDynamicStackHandler backs = all.get(BACK).getStacks();
        IDynamicStackHandler belts = all.get(BELT).getStacks();
        if (backs.getSlots() >= 1) {
            map.put(BackBeltToolManager.Place.BACK_RIGHT, backs.getStackInSlot(0));
            if (backs.getSlots() >= 2)
                map.put(BackBeltToolManager.Place.BACK_LEFT, backs.getStackInSlot(1));
        }
        if (belts.getSlots() >= 1) {
            map.put(BackBeltToolManager.Place.BELT_RIGHT, belts.getStackInSlot(0));
            if (belts.getSlots() >= 2)
                map.put(BackBeltToolManager.Place.BELT_LEFT, belts.getStackInSlot(1));
        }
        return map;
    }
}
