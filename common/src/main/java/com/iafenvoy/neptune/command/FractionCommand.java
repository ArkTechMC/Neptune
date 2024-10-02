package com.iafenvoy.neptune.command;

import com.iafenvoy.neptune.data.NeptunePlayerData;
import com.iafenvoy.neptune.fraction.Fraction;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FractionCommand {
    public static final LiteralArgumentBuilder<ServerCommandSource> FRACTION_COMMAND = literal("fraction")
            .requires(ServerCommandSource::isExecutedByPlayer)
            .then(argument("fraction_id", StringArgumentType.greedyString())
                    .suggests((context, builder) -> CommandSource.suggestIdentifiers(Fraction.values().stream().map(Fraction::id), builder))
                    .executes(ctx -> {
                        Fraction fraction = Fraction.getById(StringArgumentType.getString(ctx, "fraction_id"));
                        NeptunePlayerData.byPlayer(ctx.getSource().getPlayerOrThrow()).setFraction(fraction);
                        ctx.getSource().sendFeedback(() -> Text.translatable("command.neptune.fraction.success").append(Text.translatable(fraction.id().toTranslationKey("fraction"))), false);
                        return 1;
                    })
            );
}
