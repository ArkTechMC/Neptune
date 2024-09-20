package com.iafenvoy.neptune.object;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoundUtil {
    public static void playSound(World world, Vec3d pos, Identifier soundId, float volume, float pitch) {
        playSound(world, pos.x, pos.y, pos.z, soundId, volume, pitch);
    }

    public static void playSound(World world, double x, double y, double z, Identifier soundId, float volume, float pitch) {
        playSound(world, x, y, z, Registries.SOUND_EVENT.get(soundId), volume, pitch);
    }

    public static void playSound(World world, Vec3d pos, SoundEvent soundEvent, float volume, float pitch) {
        playSound(world, pos.x, pos.y, pos.z, soundEvent, volume, pitch);
    }

    public static void playSound(World world, double x, double y, double z, SoundEvent soundEvent, float volume, float pitch) {
        if (soundEvent == null) return;
        if (world.isClient())
            world.playSound(x, y, z, soundEvent, SoundCategory.NEUTRAL, volume, pitch, false);
        else
            world.playSound(null, VecUtil.createBlockPos(x, y, z), soundEvent, SoundCategory.NEUTRAL, volume, pitch);
    }

    public static void playPlayerSound(PlayerEntity player, Identifier soundId, float volume, float pitch) {
        playPlayerSound(player, Registries.SOUND_EVENT.get(soundId), volume, pitch);
    }

    public static void playPlayerSound(PlayerEntity player, SoundEvent soundId, float volume, float pitch) {
        playPlayerSound(player.getEntityWorld(), player.getX(), player.getY(), player.getZ(), soundId, volume, pitch);
    }

    public static void playPlayerSound(World world, double x, double y, double z, Identifier soundId, float volume, float pitch) {
        playPlayerSound(world, x, y, z, Registries.SOUND_EVENT.get(soundId), volume, pitch);
    }

    public static void playPlayerSound(World world, double x, double y, double z, SoundEvent soundEvent, float volume, float pitch) {
        if (soundEvent == null) return;
        if (world.isClient())
            world.playSound(x, y, z, soundEvent, SoundCategory.PLAYERS, volume, pitch, false);
        else
            world.playSound(null, VecUtil.createBlockPos(x, y, z), soundEvent, SoundCategory.PLAYERS, volume, pitch);
    }

    public static void stopSound(World world, Identifier soundId) {
        if (world instanceof ServerWorld serverLevel) {
            StopSoundS2CPacket stopSoundPacket = new StopSoundS2CPacket(soundId, SoundCategory.NEUTRAL);
            for (ServerPlayerEntity serverPlayer : serverLevel.getPlayers())
                serverPlayer.networkHandler.sendPacket(stopSoundPacket);
        }
    }
}
