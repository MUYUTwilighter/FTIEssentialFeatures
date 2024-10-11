package cool.muyucloud.fef.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.muyucloud.fef.FtiEssentialFeatures;
import dev.technici4n.moderndynamics.client.ber.PipeBlockEntityRenderer;
import dev.technici4n.moderndynamics.pipe.PipeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PipeBlockEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class PipeBlockEntityRendererMixin {
    @Inject(method = "render(Ldev/technici4n/moderndynamics/pipe/PipeBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
        at = @At("HEAD"), cancellable = true)
    public void redirectRender(PipeBlockEntity pipe, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (!FtiEssentialFeatures.CONFIG.shouldModernDynamicsRenderItem()) {
            ci.cancel();
        }
    }
}
