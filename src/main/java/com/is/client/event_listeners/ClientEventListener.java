package com.is.client.event_listeners;

import com.is.ISConst;
import com.is.client.data.ClientDelphiManager;
import com.is.client.events.gui.AddOverlayOverridesEvent;
import com.is.client.gui.HotbarRenders;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

// Both classes not for static methods
public final class ClientEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onGuiRender(RenderGuiOverlayEvent.Post event) {
        if (!ISConst.DEBUG) return;
        if (event.getOverlay().id() != VanillaGuiOverlay.CROSSHAIR.id()) return;

        Minecraft.getInstance().font.draw(event.getPoseStack(), "Balance: " + ClientDelphiManager.getInstance().getBalance(null), 0, 0, 0xFFFFFFFF);
    }

    public static final class ModEvents {

        @SubscribeEvent
        public void onOverlayOverridesRegistry(AddOverlayOverridesEvent event) {
            LOGGER.info("Registering gui overlay overrides");
            // Todo replace hotbar for prod. Now will only be enabled in dev environment
            if (!FMLLoader.isProduction() && ISConst.DEBUG) {
                event.addOverride(VanillaGuiOverlay.HOTBAR.type(), HotbarRenders::renderHotbar);
                event.addOverride(VanillaGuiOverlay.PLAYER_HEALTH.type(), HotbarRenders::renderHealth);

                // I'd like to render this in healths render
                event.addOverride(VanillaGuiOverlay.EXPERIENCE_BAR.type(), (gui, poseStack, delta, width, height) -> {
                    poseStack.translate(0, 5, 0);
//                poseStack.scale(0.8f, 0.8f, 1);
                    VanillaGuiOverlay.EXPERIENCE_BAR.type().overlay().render(gui, poseStack, delta, width, height);
//                poseStack.scale(1 / 0.8f, 1 / 0.8f, 1);
                    poseStack.translate(0, -5, 0);
                });
                event.addOverride(VanillaGuiOverlay.FOOD_LEVEL.type(), (gui, poseStack, delta, width, height) -> {
                });
            }
        }
    }

}
