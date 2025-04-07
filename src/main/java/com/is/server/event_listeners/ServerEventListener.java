package com.is.server.event_listeners;

import com.is.ISConst;
import com.is.capabilities.ModCapabilities;
import com.is.capabilities.abilities.AbilityCapabilityImpl;
import com.is.capabilities.delphi.DelphiCapabilityImpl;
import com.is.data.CommonEnhancedBossEventManager;
import com.is.events.GainDelphiEvent;
import com.is.network.NetworkHandler;
import com.is.server.commands.BindEntityForBossBarCommand;
import com.is.server.data.ServerAbilityManager;
import com.is.server.data.ServerDelphiManager;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

public final class ServerEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static MinecraftServer server;

    @SubscribeEvent
    public void onServerStarting(RegisterCommandsEvent event) {
        BindEntityForBossBarCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        server = event.getServer();

        CommonEnhancedBossEventManager.initialize();
        server.overworld().getDataStorage().computeIfAbsent(CommonEnhancedBossEventManager::load, CommonEnhancedBossEventManager::getInstance, "is_enhanced_boss_bars");
        ServerDelphiManager.initialize();
        ServerAbilityManager.initialize();
    }

    @SubscribeEvent
    public void blockDestroyedEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
            double price = ISConst.getDelphiByBlock(event.getState().getBlock());
            GainDelphiEvent gainDelphiEvent = new GainDelphiEvent(serverPlayer, price);
            if (gainDelphiEvent.amount != 0.0d && !MinecraftForge.EVENT_BUS.post(gainDelphiEvent)) {
                ServerDelphiManager.getInstance().transfer(serverPlayer, gainDelphiEvent.amount, false);
            }
        }
    }

    @SubscribeEvent
    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerDelphiManager.getInstance().syncPlayer(player);
            ServerAbilityManager.getInstance().syncPlayer(player);
            CommonEnhancedBossEventManager.getInstance().syncClients();
        }
    }

    @SubscribeEvent
    public void clonePlayerEvent(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getEntity() instanceof ServerPlayer player &&
                event.getOriginal() instanceof ServerPlayer original) {
            if (original.getPersistentData().contains(DelphiCapabilityImpl.NBT_KEY_NETWORTH)) {
                LOGGER.debug("Cloning players delphi capability {}", original.getPersistentData());
                player.getCapability(ModCapabilities.DELPHI).resolve().ifPresentOrElse((cap) -> {
                    cap.deserializeNBT((CompoundTag) original.getPersistentData().get(DelphiCapabilityImpl.NBT_KEY_NETWORTH));
                }, () -> LOGGER.warn("No delphi cap attached on clone"));
                player.getCapability(ModCapabilities.ABILITIES).resolve().ifPresentOrElse((cap) -> {
                    cap.deserializeNBT((CompoundTag) original.getPersistentData().get(AbilityCapabilityImpl.NBT_KEY_ABILITIES));
                }, () -> LOGGER.warn("No ability cap attached on clone"));
            } else {
                LOGGER.warn("No saved data was found");
            }
        }
    }

    @SubscribeEvent
    public void playerDiedEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ModCapabilities.DELPHI).resolve().ifPresentOrElse((cap) -> {
                player.getPersistentData().put(DelphiCapabilityImpl.NBT_KEY_NETWORTH, cap.serializeNBT());
            }, () -> LOGGER.warn("No delphi cap attached"));
            player.getCapability(ModCapabilities.ABILITIES).resolve().ifPresentOrElse((cap) -> {
                player.getPersistentData().put(AbilityCapabilityImpl.NBT_KEY_ABILITIES, cap.serializeNBT());
            }, () -> LOGGER.warn("No ability cap attached"));
        }
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static final class ModEvents {

        @SubscribeEvent
        public void commonSetup(FMLCommonSetupEvent event) {
            LOGGER.info("Common setup event...");
            NetworkHandler.registerPackets();
        }

    }
}
