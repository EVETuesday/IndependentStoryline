package com.is.client.event_listeners;

import com.is.ISConst;
import com.is.client.data.ClientAbilityManager;
import com.is.client.data.ClientDelphiManager;
import com.is.client.data.ClientEnhancedBossEvent;
import com.is.client.data.ClientEnhancedBossEventManager;
import com.is.client.events.gui.AddOverlayOverridesEvent;
import com.is.client.gui.AbilitiesShopScreen;
import com.is.client.gui.utils.GuiUtils;
import com.is.data.DelphiItemType;
import com.is.events.DelphiBalanceChangedEvent;
import com.is.items.IItemWithTooltip;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

import java.util.UUID;

// Both classes not for static methods
public final class ClientEventListener {

    private static final UUID DELPHI_ITEM_PROGRESS_BAR_ID = UUID.fromString("911b9bf5-a721-43ed-9a0b-1d5f1214f55a");

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation DELPHI_ITEM_PROGRESS_TEXTURE = ISConst.rl("textures/gui/delphi_item_progress_bar.png");

    @SubscribeEvent
    public void onGuiRender(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id() != VanillaGuiOverlay.HOTBAR.id()) return;

        String value = I18n.get("is.gui.delphi_shop.button.balance", ClientDelphiManager.getInstance().getBalance(null));
        Minecraft.getInstance().font.drawShadow(event.getPoseStack(), value, 1, 1, AbilitiesShopScreen.BASE_COLOR);
        RenderSystem.setShaderTexture(0, AbilitiesShopScreen.DELPHI_COIN_TEXTURE);
        GuiComponent.blit(event.getPoseStack(), 1 + Minecraft.getInstance().font.width(value) + 1, 1,
                0, 0,
                10, 10,
                10, 10
        );
        if (ISConst.DEBUG)
            Minecraft.getInstance().font.drawShadow(event.getPoseStack(), "Networth: " + ClientDelphiManager.getInstance().getNetworth(null), 1, 10, AbilitiesShopScreen.BASE_COLOR);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDelphiBalanceChanged(DelphiBalanceChangedEvent event) {
        if (event.player instanceof LocalPlayer) {
            updateItemDelphiData();
        }
    }

    public static void updateItemDelphiData() {
        DelphiItemType currentItem = ClientAbilityManager.getInstance().getCurrentItem(null);
        if (currentItem != DelphiItemType.NULL) {
            double lastReqDelphies = currentItem.ordinal() > 0 ? DelphiItemType.values()[currentItem.ordinal() - 1].requiredDelphies: 0;
            double deltaReqDelphies = currentItem.requiredDelphies - lastReqDelphies;

            ClientEnhancedBossEvent enhancedBossEvent = ClientEnhancedBossEventManager.getInstance().getBossBar(DELPHI_ITEM_PROGRESS_BAR_ID).orElse(new ClientEnhancedBossEvent(
                    DELPHI_ITEM_PROGRESS_BAR_ID,
                    ISConst.rl("textures/gui/delphi_item_progress_bar.png"),
                    Component.empty(),
                    (float) deltaReqDelphies,
                    false,
                    false,
                    false,
                    false
            ));
            if (enhancedBossEvent.getMaxValue() != (float) deltaReqDelphies) enhancedBossEvent.setMaxValue((float) deltaReqDelphies);

            enhancedBossEvent.setValue((float) (ClientDelphiManager.getInstance().getNetworth(null) - lastReqDelphies));
            ClientEnhancedBossEventManager.getInstance().update(enhancedBossEvent);
        } else {
            ClientEnhancedBossEventManager.getInstance().markRemoved(DELPHI_ITEM_PROGRESS_BAR_ID);
        }
    }

    @SubscribeEvent
    public void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ClientDelphiManager.initialize();
        ClientAbilityManager.initialize();
        ClientEnhancedBossEventManager.getInstance();
    }

    @SubscribeEvent
    public void onLeaveJoin(PlayerEvent.PlayerLoggedOutEvent event) {
        ClientDelphiManager.invalidate();
        ClientAbilityManager.invalidate();
        ClientEnhancedBossEventManager.invalidate();
    }

    @SubscribeEvent
    public void onAddItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IItemWithTooltip tooltip) {
            event.getToolTip().addAll(1, tooltip.getTooltip(event.getItemStack(), event.getEntity()));
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
