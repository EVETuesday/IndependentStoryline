package com.is.server.commands;

import com.is.data.CommonEnhancedBossEvent;
import com.is.data.CommonEnhancedBossEventManager;
import com.is.network.NetworkHandler;
import com.is.network.packets.S2CSyncEnhancedBossBarPacket;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class BindEntityForBossBarCommand {

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("bind_enhanced_boss_bar").requires((p_139068_) -> p_139068_.hasPermission(2)).then(Commands.argument("target", EntityArgument.entity()).executes((p_139066_) -> {
            Entity entity = EntityArgument.getEntity(p_139066_, "target");
            if (entity instanceof LivingEntity livingEntity) {
                CommonEnhancedBossEventManager.getInstance().bindOnEntity(livingEntity, new CommonEnhancedBossEvent(
                        livingEntity.getUUID(),
                        livingEntity.getName(),
                        livingEntity.getMaxHealth(),
                        false,
                        false,
                        false
                ));
            }
            return 1;
        })));
    }

}
