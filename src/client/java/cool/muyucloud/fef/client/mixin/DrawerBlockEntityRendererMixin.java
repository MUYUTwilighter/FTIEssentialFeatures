package cool.muyucloud.fef.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.muyucloud.fef.FtiEssentialFeatures;
import io.github.mattidragon.extendeddrawers.client.renderer.DrawerBlockEntityRenderer;
import io.github.mattidragon.extendeddrawers.storage.DrawerSlot;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawerBlockEntityRenderer.class)
public class DrawerBlockEntityRendererMixin {
    @Inject(method = "renderSlot",
            at = @At("HEAD"), cancellable = true)
    public void onRender(DrawerSlot storage, boolean small, int light, PoseStack matrices, MultiBufferSource vertexConsumers, int seed, int overlay, BlockPos pos, Level world, CallbackInfo ci) {
        if (FtiEssentialFeatures.CONFIG.getExtendedDrawerCompletelyOff() && storage.isHidden()) {
            ci.cancel();
        }
    }
}
