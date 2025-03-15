package com.is.capabilities.delphi;

import com.is.ISConst;
import com.is.capabilities.ModCapabilities;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DelphiCapabilityAttacher {

    private static final Logger LOGGER = LogUtils.getLogger();

    private DelphiCapabilityAttacher() {}

    private static final class DelphiCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        private static final ResourceLocation ID = ISConst.rl("delphi_cap");

        private final IDelphiCapability inst = new DelphiCapabilityImpl();
        private final LazyOptional<IDelphiCapability> lazyOpt = LazyOptional.of(() -> inst);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilities.DELPHI.orEmpty(cap, lazyOpt);
        }

        @Override
        public CompoundTag serializeNBT() {
            return inst.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            inst.deserializeNBT(nbt);
        }
    }

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            LOGGER.info("Attaching capabilities...");
            event.addCapability(DelphiCapabilityProvider.ID, new DelphiCapabilityProvider());
        }
    }
}
