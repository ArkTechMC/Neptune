package com.iafenvoy.neptune.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

@Environment(EnvType.CLIENT)
public class BossBarRenderHelper {
    private static final Identifier BARS_TEXTURE = new Identifier("textures/gui/bars.png");
    private static final Map<Class<? extends Entity>, BossBarInfo> infos = new HashMap<>();

    public static boolean render(DrawContext context, int x, int y, BossBar bossBar, int width, int height) {
        for (BossBarInfo info : infos.values())
            if (info.bossBarId.contains(bossBar.getUuid())) {
                if (height == 5) {
                    context.drawTexture(BARS_TEXTURE, x, y, 0, bossBar.getColor().ordinal() * 5 * 2 + 5, width, 5);
                    context.drawTexture(info.texture, x - 10, y - 7, 0, 0, 0, 206, 19, 206, 19);
                }
                if (info.disableName)
                    bossBar.setName(Text.literal(""));
                return true;
            }
        return false;
    }

    public static void addBossBar(Class<? extends Entity> entityClass, UUID uuid) {
        if (!infos.containsKey(entityClass)) return;
        infos.get(entityClass).bossBarId().add(uuid);
    }

    public static void removeBossBar(Class<? extends Entity> entityClass, UUID uuid) {
        if (!infos.containsKey(entityClass)) return;
        infos.get(entityClass).bossBarId().remove(uuid);
    }

    public static void addBossBarType(Class<? extends Entity> entityClass, Identifier texture, boolean disableName) {
        infos.put(entityClass, new BossBarInfo(texture, new ArrayList<>(), disableName));
    }

    private record BossBarInfo(Identifier texture, List<UUID> bossBarId, boolean disableName) {
    }
}
