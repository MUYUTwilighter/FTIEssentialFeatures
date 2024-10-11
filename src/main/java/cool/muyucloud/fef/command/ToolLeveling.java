package cool.muyucloud.fef.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.fef.FtiEssentialFeatures;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class ToolLeveling {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("tool_leveling");
        LiteralArgumentBuilder<CommandSourceStack> maxEnchantLevel = Commands.literal("max_enchant_level").executes(context -> {
            context.getSource().sendSuccess(() -> Component.literal(String.valueOf(FtiEssentialFeatures.CONFIG.getTlrMaxEnchantLevel())), true);
            return 0;
        });
        maxEnchantLevel.then(Commands.argument("value", IntegerArgumentType.integer()).executes(context -> {
            int value = IntegerArgumentType.getInteger(context, "value");
            FtiEssentialFeatures.CONFIG.setTlrMaxEnchantLevel(value);
            return 1;
        }));
        maxEnchantLevel.requires(source -> source.hasPermission(2));
        root.then(maxEnchantLevel);
        dispatcher.register(root);
    }
}
