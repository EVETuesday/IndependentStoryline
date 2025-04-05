package com.is.client.network.handlers;

import com.is.client.data.ClientAbilityManager;
import com.is.client.gui.AbilitiesShopScreen;
import com.is.network.AbstractPacketHandler;
import com.is.network.packets.S2CSyncAbilitiesPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncAbilitiesPacketHandler extends AbstractPacketHandler<S2CSyncAbilitiesPacket> {

    @Override
    public boolean handle(S2CSyncAbilitiesPacket data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientAbilityManager.getInstance().setAbilities(null, data.abilities);
            ClientAbilityManager.getInstance().setCurrentItem(null, data.currentItem);
            if (Minecraft.getInstance().screen instanceof AbilitiesShopScreen abilitiesShopScreen) {
                abilitiesShopScreen.init(abilitiesShopScreen.getMinecraft(), abilitiesShopScreen.width, abilitiesShopScreen.height);
            }
        });
        return true;
    }

}
