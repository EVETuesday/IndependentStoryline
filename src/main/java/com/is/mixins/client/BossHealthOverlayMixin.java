package com.is.mixins.client;

import com.is.client.data.ClientEnhancedBossEvent;
import com.is.client.data.ClientEnhancedBossEventManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin extends GuiComponent {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void renderInject(PoseStack pPoseStack, CallbackInfo ci) {
        List<ClientEnhancedBossEvent> bossBars = ClientEnhancedBossEventManager.getInstance().getAllBossBars();
        if (!bossBars.isEmpty()) {
            bossBars.forEach((bar) -> bar.render(pPoseStack));
            ci.cancel();
        }
    }

    @Inject(method = "shouldPlayMusic", at = @At("HEAD"), cancellable = true)
    public void shouldPlayMusicInject(CallbackInfoReturnable<Boolean> cir) {
        List<ClientEnhancedBossEvent> bossBars = ClientEnhancedBossEventManager.getInstance().getAllBossBars();
        if (!bossBars.isEmpty()) {
            cir.setReturnValue(bossBars.stream().anyMatch(ClientEnhancedBossEvent::shouldPlayBossMusic));
        }
    }

    @Inject(method = "shouldDarkenScreen", at = @At("HEAD"), cancellable = true)
    public void shouldDarkenScreenInject(CallbackInfoReturnable<Boolean> cir) {
        List<ClientEnhancedBossEvent> bossBars = ClientEnhancedBossEventManager.getInstance().getAllBossBars();
        if (!bossBars.isEmpty()) {
            cir.setReturnValue(bossBars.stream().anyMatch(ClientEnhancedBossEvent::shouldDarkenScreen));
        }
    }

    @Inject(method = "shouldCreateWorldFog", at = @At("HEAD"), cancellable = true)
    public void shouldCreateWorldFogInject(CallbackInfoReturnable<Boolean> cir) {
        List<ClientEnhancedBossEvent> bossBars = ClientEnhancedBossEventManager.getInstance().getAllBossBars();
        if (!bossBars.isEmpty()) {
            cir.setReturnValue(bossBars.stream().anyMatch(ClientEnhancedBossEvent::shouldCreateWorldFog));
        }
    }

}
