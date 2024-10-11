package cool.muyucloud.fef.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.fef.FtiEssentialFeatures;
import cool.muyucloud.fef.util.Config;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public class FefClientCommand {
    private static final Config CONFIG = FtiEssentialFeatures.CONFIG;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<FabricClientCommandSource> root = ClientCommandManager.literal("fef");
        appendModernDynamics(root);
        appendExtendedDrawers(root);
        dispatcher.register(root);
    }

    private static void appendModernDynamics(LiteralArgumentBuilder<FabricClientCommandSource> parent) {
        LiteralArgumentBuilder<FabricClientCommandSource> root = ClientCommandManager.literal("modern_dynamics");
        LiteralArgumentBuilder<FabricClientCommandSource> renderItem = ClientCommandManager.literal("render_item").executes(context -> {
            context.getSource().sendFeedback(Component.literal(String.valueOf(CONFIG.shouldModernDynamicsRenderItem())));
            return 0;
        });
        renderItem.then(ClientCommandManager.argument("value", BoolArgumentType.bool()).executes(context -> {
            boolean value = BoolArgumentType.getBool(context, "value");
            CONFIG.setModernDynamicsRenderItem(value);
            return 1;
        }));
        renderItem.then(ClientCommandManager.literal("toggle").executes(context -> {
            CONFIG.toggleModernDynamicsRenderItem();
            return 1;
        }));
        root.then(renderItem);
        parent.then(root);
    }

    private static void appendExtendedDrawers(LiteralArgumentBuilder<FabricClientCommandSource> parent) {
        LiteralArgumentBuilder<FabricClientCommandSource> root = ClientCommandManager.literal("extended_drawers");
        LiteralArgumentBuilder<FabricClientCommandSource> completelyOff = ClientCommandManager.literal("completely_off").executes(context -> {
            context.getSource().sendFeedback(Component.literal(String.valueOf(CONFIG.getExtendedDrawerCompletelyOff())));
            return 0;
        });
        completelyOff.then(ClientCommandManager.argument("value", BoolArgumentType.bool()).executes(context -> {
            boolean value = BoolArgumentType.getBool(context, "value");
            CONFIG.setExtendedDrawerCompletelyOff(value);
            return 1;
        }));
        completelyOff.then(ClientCommandManager.literal("toggle").executes(context -> {
            CONFIG.toggleExtendedDrawerCompletelyOff();
            return 1;
        }));
        root.then(completelyOff);
        parent.then(root);
    }
}
