package cool.muyucloud.fef;

import cool.muyucloud.fef.command.ToolLeveling;
import cool.muyucloud.fef.util.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public class FtiEssentialFeatures implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("FtiEssentialFeatures");
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("ftiEssentialFeatures.json");
    public static Config CONFIG = Config.load(CONFIG_PATH);

    @Override
    public void onInitialize() {
        LOGGER.info("FTI Essential Features loaded");
        LOGGER.info("This mod is dedicated for mod pack Farm The Ingots, you can use it outside the mod pack but we won't provide any support.");
        ServerLifecycleEvents.SERVER_STOPPING.register(FtiEssentialFeatures::onServerStopping);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ToolLeveling.register(dispatcher));
    }

    public static void onServerStopping(MinecraftServer server) {
        CONFIG.dump(CONFIG_PATH);
    }
}
