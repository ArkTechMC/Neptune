package com.iafenvoy.neptune.command;

import com.iafenvoy.neptune.data.NeptunePlayerData;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SkinCommand {
    public static final LiteralArgumentBuilder<ServerCommandSource> SKIN_COMMAND = literal("skin")
            .requires(ServerCommandSource::isExecutedByPlayer)
            .requires(context -> !context.getServer().isDedicated() || context.hasPermissionLevel(4))
            .then(argument("player", EntityArgumentType.players())
                    .executes(ctx -> {
                        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "player");
                        for (ServerPlayerEntity player : players)
                            NeptunePlayerData.byPlayer(player).setUsingTexture(null);
                        return 1;
                    })
                    .then(argument("texture", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                try {
                                    Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "player");
                                    for (ServerPlayerEntity player : players)
                                        NeptunePlayerData.byPlayer(player).setUsingTexture(new Identifier(StringArgumentType.getString(ctx, "texture")));
                                    return 1;
                                } catch (Exception e) {
                                    throw new CommandSyntaxException(new SimpleCommandExceptionType(e::getMessage), e::getMessage);
                                }
                            })
                    )
            );
}
