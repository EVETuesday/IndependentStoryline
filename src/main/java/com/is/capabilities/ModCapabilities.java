package com.is.capabilities;

import com.is.ISConst;
import com.is.capabilities.abilities.IAbilityCapability;
import com.is.capabilities.delphi.IDelphiCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ModCapabilities {

    private ModCapabilities() {}

    public static Capability<IDelphiCapability> DELPHI = CapabilityManager.get(new CapabilityToken<>() {});
    public static Capability<IAbilityCapability> ABILITIES = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IDelphiCapability.class);
        event.register(IAbilityCapability.class);
    }
}
