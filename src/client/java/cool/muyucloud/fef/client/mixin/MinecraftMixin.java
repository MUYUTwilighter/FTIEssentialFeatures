package cool.muyucloud.fef.client.mixin;

import cool.muyucloud.fef.FtiEssentialFeatures;
import cool.muyucloud.fef.client.FtiEssentialFeaturesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Redirect(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusic()Ljava/util/Optional;"))
    private Optional<Music> redirectGetBackgroundMusic(Biome instance) {
        final ResourceLocation GLACIO = new ResourceLocation("ad_astra:glacio");
        assert level != null;
        ResourceLocation dim = level.dimensionTypeId().location();
        if (dim.equals(GLACIO)) {
            return FtiEssentialFeaturesClient.GLACIO_MUSIC;
        } else {
            return instance.getBackgroundMusic();
        }
    }
}
