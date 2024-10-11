package cool.muyucloud.fef.client;

import cool.muyucloud.fef.client.command.FefClientCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

@Environment(EnvType.CLIENT)
public class FtiEssentialFeaturesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> FefClientCommand.register(dispatcher));
    }
}
