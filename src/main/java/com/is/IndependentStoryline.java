package com.is;

import com.EveTuPart.Items.ModCommands;
import com.EveTuPart.Items.ModItems;
import com.ObliviscorPart.Effects.ModEffects;
import com.is.client.ModKeyBinds;
import com.is.client.entity.render.StoneStyletRender;
import com.is.client.event_listeners.ClientEventListener;
import com.is.client.gui.OverlayOverrides;
import com.is.entitys.ModEntitys;
import com.is.server.event_listeners.ServerEventListener;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.Environment;
import org.slf4j.Logger;



@Mod(ISConst.MODID)
public class IndependentStoryline {

    private static final Logger LOGGER = LogUtils.getLogger();

    private ServerEventListener forgeListener;
    private ServerEventListener.ModEvents modListener;

    @SuppressWarnings("removal") // >:D
    public IndependentStoryline() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (Environment.get().getDist().isClient()) new Client(eventBus);

        eventBus.register((modListener = new ServerEventListener.ModEvents()));
        MinecraftForge.EVENT_BUS.register((forgeListener = new ServerEventListener()));
        ModEntitys.register(eventBus);
        ModEffects.register(eventBus);
    }
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {  ModCommands.register(event.getDispatcher()); }


    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public static class Client {


        private final IEventBus modEventBus;

        private final ClientEventListener forgeListener;
        private final ClientEventListener.ModEvents modListener;

        private OverlayOverrides overlayOverrides;

        public Client(IEventBus modEventBus) {
            this.modEventBus = modEventBus;

            this.modEventBus.addListener(this::commonSetup);

            modEventBus.register((modListener = new ClientEventListener.ModEvents()));
            MinecraftForge.EVENT_BUS.register((forgeListener = new ClientEventListener()));
            ModItems.ITEMS.register(modEventBus);
        }

        public void commonSetup(FMLCommonSetupEvent event) {
            overlayOverrides = new OverlayOverrides(modEventBus);
            ModKeyBinds.init();
            EntityRenderers.register(ModEntitys.STONE_STYLET_PROJECTILE.get(), StoneStyletRender::new);
        }

    }


}
