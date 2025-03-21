package com.is.client.event_listeners;

import com.is.ISConst;
import com.is.client.data.ClientAbilityManager;
import com.is.client.data.ClientDelphiManager;
import com.is.client.events.gui.AddOverlayOverridesEvent;
import com.is.client.gui.AbilitiesShopScreen;
import com.is.client.gui.HotbarRenders;
import com.is.data.DelphiItemType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.world.BossEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

// Both classes not for static methods
public final class ClientEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation DELPHI_ITEM_PROGRESS_TEXTURE = ISConst.rl("textures/gui/delphi_item_progress_bar.png");

    @SubscribeEvent
    public void onGuiRender(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id() != VanillaGuiOverlay.HOTBAR.id()) return;

        Minecraft.getInstance().font.drawShadow(event.getPoseStack(), I18n.get("is.gui.delphi_shop.button.balance", ClientDelphiManager.getInstance().getBalance(null)), 1, 1, AbilitiesShopScreen.BASE_COLOR);
        if (ISConst.DEBUG) Minecraft.getInstance().font.drawShadow(event.getPoseStack(), "Networth: " + ClientDelphiManager.getInstance().getNetworth(null), 1, 10, AbilitiesShopScreen.BASE_COLOR);

        DelphiItemType currentItem = ClientAbilityManager.getInstance().getCurrentItem(null);
        if (currentItem != DelphiItemType.NULL) {
            RenderSystem.setShaderTexture(0, DELPHI_ITEM_PROGRESS_TEXTURE);
            double lastReqDelphies = 0;
            if (currentItem.ordinal() > 0) {
                lastReqDelphies = DelphiItemType.values()[currentItem.ordinal() - 1].requiredDelphies;
            }
            double itemProgress = (ClientDelphiManager.getInstance().getNetworth(null) - lastReqDelphies) / (currentItem.requiredDelphies - lastReqDelphies);
            GuiComponent.blit(event.getPoseStack(), (event.getWindow().getGuiScaledWidth() - 256) / 2, 5, 0, 32, 256, 32, 256, 64);
            GuiComponent.blit(event.getPoseStack(), (event.getWindow().getGuiScaledWidth() - 256) / 2, 5, 0, 0, (int) (256 * itemProgress), 32, 256, 64);

            String data = String.valueOf(ClientDelphiManager.getInstance().getNetworth(null) - lastReqDelphies);
            Minecraft.getInstance().font.draw(event.getPoseStack(), data, (event.getWindow().getGuiScaledWidth()) / 2f - Minecraft.getInstance().font.width(data) - 3, 18f, AbilitiesShopScreen.BASE_COLOR);
            Minecraft.getInstance().font.draw(event.getPoseStack(), String.valueOf(currentItem.requiredDelphies - lastReqDelphies), (event.getWindow().getGuiScaledWidth()) / 2f + 4, 18f, AbilitiesShopScreen.BASE_COLOR);
        }
    }

    public static final class ModEvents {

        @SubscribeEvent
        public void onOverlayOverridesRegistry(AddOverlayOverridesEvent event) {
            LOGGER.info("Registering gui overlay overrides");
            // Todo replace hotbar for prod. Now will only be enabled in dev environment
            if (!FMLLoader.isProduction() && ISConst.DEBUG) {
//                event.addOverride(VanillaGuiOverlay.HOTBAR.type(), HotbarRenders::renderHotbar);
//                event.addOverride(VanillaGuiOverlay.PLAYER_HEALTH.type(), HotbarRenders::renderHealth);
//
//                // I'd like to render this in healths render
//                event.addOverride(VanillaGuiOverlay.EXPERIENCE_BAR.type(), (gui, poseStack, delta, width, height) -> {
//                    poseStack.translate(0, 5, 0);
////                poseStack.scale(0.8f, 0.8f, 1);
//                    VanillaGuiOverlay.EXPERIENCE_BAR.type().overlay().render(gui, poseStack, delta, width, height);
////                poseStack.scale(1 / 0.8f, 1 / 0.8f, 1);
//                    poseStack.translate(0, -5, 0);
//                });
//                event.addOverride(VanillaGuiOverlay.FOOD_LEVEL.type(), (gui, poseStack, delta, width, height) -> {
//                });
            }
        }
    }

}
