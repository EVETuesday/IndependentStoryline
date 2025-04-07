package com.is.network;

import com.is.ISConst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class NetworkHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ISConst.MODID, "main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("unchecked")
    public static void registerPackets() {
        LOGGER.info("Registering messages {}", INSTANCE);
        int i = 0;
        for (Packets packetData : Packets.values()) {
            Class<? extends AbstractPacketHandler<?>> handlerClazz;
            if ((FMLEnvironment.dist.isClient() && packetData.playTo == NetworkDirection.PLAY_TO_CLIENT) ||
                    (packetData.playTo == NetworkDirection.PLAY_TO_SERVER)) {
                handlerClazz = packetData.handlerClazz;
            } else {
                handlerClazz = null;
            }

            INSTANCE.messageBuilder(packetData.packetClazz, i++, packetData.playTo)
                    .encoder(AbstractPacket::toBuf)
                    .decoder((packetBuffer) -> {
                        try {
                            return instantiatePacket(packetBuffer, (Class<AbstractPacket>) packetData.packetClazz);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .consumer((msg, context) -> {
                        AbstractPacketHandler<Object> handlerClass;
                        try {
                            assert handlerClazz != null;
                            handlerClass = (AbstractPacketHandler<Object>) handlerClazz.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }

                        handlerClass.handle(msg, context);
                    }).add();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiatePacket(FriendlyByteBuf packetBuffer, Class<AbstractPacket> packetClazz) throws Exception {
        return (T) packetClazz.cast(packetClazz.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(packetBuffer));
    }
}
