package cool.muyucloud.fef.mixin;

import cool.muyucloud.fef.FtiEssentialFeatures;
import cool.muyucloud.fef.refection.TNetReflection;
import dev.architectury.networking.NetworkManager;
import ins.tlr.network.TNet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TNet.class)
public class TNetMixin {
    @Inject(
        method = "lambda$receive$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"
        ),
        cancellable = true
    )
    private static void onApplyEnchantment(NetworkManager.PacketContext pack, TNet a, CallbackInfo ci) {
        TNetReflection tNet = TNetReflection.of(a);
        int level = tNet.getValue();
        if (level >= FtiEssentialFeatures.CONFIG.getTlrMaxEnchantLevel()) {
            ci.cancel();
        }
    }
}
