package cool.muyucloud.fef.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    @Shadow protected abstract void fudgeSpawnLocation(ServerLevel serverLevel);

    @Shadow @Final public MinecraftServer server;

    @Shadow public abstract void playNotifySound(SoundEvent soundEvent, SoundSource soundSource, float f, float g);

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Inject(method = "teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void onTeleport(ServerLevel serverLevel, double d, double e, double f, float g, float h, CallbackInfo ci) {
        if (preventEnterGlacio(serverLevel)) {
            ci.cancel();
        }
    }

    @Inject(method = "changeDimension", at = @At("HEAD"), cancellable = true)
    private void onChangeDimension(ServerLevel serverLevel, CallbackInfoReturnable<Entity> cir) {
        if (this.preventEnterGlacio(serverLevel)) {
            cir.cancel();
        }
    }

    @Unique
    private boolean preventEnterGlacio(ServerLevel level) {
        ResourceLocation dim = level.dimensionTypeId().location();
        final ResourceLocation GLACIO = new ResourceLocation("ad_astra:glacio");
        if (dim.equals(GLACIO) && !isSuitValid()) {
            ServerLevel overworld = this.server.getLevel(Level.OVERWORLD);
            this.fudgeSpawnLocation(overworld);
            MutableComponent message = Component.translatable("chat.player.move.to.world.glacio");
            this.sendSystemMessage(message);
            MobEffectInstance effect = new MobEffectInstance(MobEffects.WEAKNESS, 100, 1);
            this.addEffect(effect);
            this.playNotifySound(SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.AMBIENT, 1.0F, 1.0F);
            this.playNotifySound(SoundEvents.PORTAL_TRAVEL, SoundSource.AMBIENT, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Unique
    private boolean isSuitValid() {
        Item helmet = this.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chestplate = this.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item leggings = this.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item boots = this.getItemBySlot(EquipmentSlot.FEET).getItem();
        final Item validHelmet = BuiltInRegistries.ITEM.get(new ResourceLocation("ad_astra:jet_suit_helmet"));
        final Item validChestplate = BuiltInRegistries.ITEM.get(new ResourceLocation("ad_astra:jet_suit"));
        final Item validLeggings = BuiltInRegistries.ITEM.get(new ResourceLocation("ad_astra:jet_suit_pants"));
        final Item validBoots = BuiltInRegistries.ITEM.get(new ResourceLocation("ad_astra:jet_suit_boots"));
        return helmet == validHelmet && chestplate == validChestplate && leggings == validLeggings && boots == validBoots;
    }
}
