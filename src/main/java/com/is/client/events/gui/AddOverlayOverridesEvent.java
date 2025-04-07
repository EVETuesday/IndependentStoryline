package com.is.client.events.gui;

import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Event for gui overlays overrides registering.
 * Uses MOD event bus.
 **/
public class AddOverlayOverridesEvent extends Event implements IModBusEvent {

    private final Map<NamedGuiOverlay, IGuiOverlay> OVERRIDES = new HashMap<>();

    public AddOverlayOverridesEvent() {

    }

    public void addOverride(NamedGuiOverlay overlay, IGuiOverlay renderFunc) {
        OVERRIDES.put(overlay, renderFunc);
    }

    public Map<NamedGuiOverlay, IGuiOverlay> getOverrides() {
        return OVERRIDES;
    }
}
