package com.iafenvoy.neptune.render.tool.fabric;

import com.iafenvoy.neptune.render.tool.BackBeltToolManager;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class BackBeltToolManagerImpl extends BackBeltToolManager {
    public static Map<BackBeltToolManager.Place, ItemStack> getAllEquipped(PlayerEntity player) {
        Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(player);
        Map<BackBeltToolManager.Place, ItemStack> map = new HashMap<>();
        if (optional.isEmpty()) return map;
        TrinketComponent component = optional.get();
        List<Pair<SlotReference, ItemStack>> all = component.getAllEquipped();
        List<Pair<SlotReference, ItemStack>> backs = all.stream().filter(x -> isBack(x.getLeft())).toList();
        List<Pair<SlotReference, ItemStack>> belts = all.stream().filter(x -> isBelt(x.getLeft())).toList();
        if (!backs.isEmpty()) {
            map.put(BackBeltToolManager.Place.BACK_RIGHT, backs.get(0).getRight());
            if (backs.size() >= 2)
                map.put(BackBeltToolManager.Place.BACK_LEFT, backs.get(1).getRight());
        }
        if (!belts.isEmpty()) {
            map.put(BackBeltToolManager.Place.BELT_RIGHT, belts.get(0).getRight());
            if (belts.size() >= 2)
                map.put(BackBeltToolManager.Place.BELT_LEFT, belts.get(1).getRight());
        }
        return map;
    }

    public static boolean isBack(SlotReference reference) {
        SlotType type = reference.inventory().getSlotType();
        return type.getGroup().equals("chest") && type.getName().equals(BACK);
    }

    public static boolean isBelt(SlotReference reference) {
        SlotType type = reference.inventory().getSlotType();
        return type.getGroup().equals("legs") && type.getName().equals(BELT);
    }
}
