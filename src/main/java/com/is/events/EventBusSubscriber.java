package com.is.events;

import com.EveTuPart.Items.ModCommands;
import com.is.ISConst;
import com.is.IndependentStoryline;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EventBusSubscriber {
    @Mod.EventBusSubscriber(modid = ISConst.MODID)
    public class ModEvents {

        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            ModCommands.register(event.getDispatcher());
        }
}}
