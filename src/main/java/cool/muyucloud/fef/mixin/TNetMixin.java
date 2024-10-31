package cool.muyucloud.fef.mixin;

import cool.muyucloud.fef.FtiEssentialFeatures;
import dev.architectury.networking.NetworkManager;
import ins.tlr.network.TNet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TNet.class)
public class TNetMixin {
    @Shadow @Final private int level;

    @Inject(
        method = "lambda$receive$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"
        ),
        cancellable = true
    )
    private static void onApplyEnchantment(NetworkManager.PacketContext pack, TNet a, CallbackInfo ci) {
        TNetMixin tNet = (TNetMixin) (Object) a;
        assert tNet != null;
        if (tNet.level >= FtiEssentialFeatures.CONFIG.getTlrMaxEnchantLevel()) {
            ci.cancel();
        }
    }
}
