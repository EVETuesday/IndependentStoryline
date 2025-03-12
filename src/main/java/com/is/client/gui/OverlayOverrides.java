package com.is.client.gui;

import com.google.common.collect.ImmutableMap;
import com.is.client.events.gui.AddOverlayOverridesEvent;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

import java.util.Map;

public final class OverlayOverrides {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<NamedGuiOverlay, IGuiOverlay> overrides;

    public OverlayOverrides(IEventBus eventBus) {
        AddOverlayOverridesEvent event = new AddOverlayOverridesEvent();
//        Todo for some reason eventBus.post(event) returns false event tho event is posted and listened
//        if (eventBus.post(event)) {
//            overrides = ImmutableMap.copyOf(event.getOverrides());
//        } else {
//            LOGGER.warn("Failed to post overlay overrides event, event is canceled {}", event.isCanceled());
//            overrides = ImmutableMap.of();
//        }
        eventBus.post(event);
        overrides = ImmutableMap.copyOf(event.getOverrides());
        MinecraftForge.EVENT_BUS.addListener(this::guiDraw);
        LOGGER.info("Initialized overrides");
    }

    @SubscribeEvent
    public void guiDraw(RenderGuiOverlayEvent.Pre event) {
        IGuiOverlay override = overrides.get(event.getOverlay());
        if (override == null) return;
        Minecraft minecraft = Minecraft.getInstance();

        event.setCanceled(true);
        override.render(
                (ForgeGui) minecraft.gui, event.getPoseStack(), event.getPartialTick(),
                minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledWidth()
        );
    }


}
