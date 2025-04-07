package com.is.client.network.handlers;

import com.is.client.gui.magazines.MagazineScreen;
import com.is.network.AbstractPacketHandler;
import com.is.network.packets.S2COpenMagazinePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenMagazinePacketHandler extends AbstractPacketHandler<S2COpenMagazinePacket> {

    @Override
    public boolean handle(S2COpenMagazinePacket data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new MagazineScreen(data.magazineType));
        });
        return true;
    }

}
