package cool.muyucloud.fef.client;

import cool.muyucloud.fef.client.command.FefClientCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FtiEssentialFeaturesClient implements ClientModInitializer {
    public static final Optional<Music> GLACIO_MUSIC = Optional.of(
        new Music(
            Holder.Reference.createIntrusive(
                null,
                SoundEvent.createVariableRangeEvent(
                    new ResourceLocation("fef:music.glacio")
                )
            ),
            12000,
            24000,
            false
        )
    );

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> FefClientCommand.register(dispatcher));
    }
}
