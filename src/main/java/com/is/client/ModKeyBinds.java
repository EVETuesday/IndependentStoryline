package com.is.client;

import com.is.ISConst;
import com.is.client.gui.AbilitiesShopScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ModKeyBinds {

    private static final List<KeyBind> KEYBINDS = new ArrayList<>();

    public static final KeyMapping OPEN_DELPHI_SHOP_KEYBIND = new KeyBind(
            "key." + ISConst.MODID + ".open_delphi_shop",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "key.categories.misc"
    ) {
        @Override
        public void doClick() {
            Minecraft.getInstance().setScreen(new AbilitiesShopScreen());
        }
    };

    private ModKeyBinds() {
    }

    public static void init() {
        //just so all keybinds instantiate
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        KEYBINDS.forEach(event::register);
    }

    @SubscribeEvent
    public static void handleInput(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            KEYBINDS.forEach(keyBind -> {
                if (keyBind.consumeClick()) {
                    keyBind.doClick();
                }
            });
        }
    }

    public abstract static class KeyBind extends KeyMapping {

        public KeyBind(String description, IKeyConflictContext keyConflictContext, InputConstants.Type inputType, int keyCode, String category) {
            super(description, keyConflictContext, inputType, keyCode, category);
            KEYBINDS.add(this);
        }

        public abstract void doClick();
    }
}
