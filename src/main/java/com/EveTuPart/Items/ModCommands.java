package com.EveTuPart.Items;

import com.is.ISConst;
import com.is.entitys.ModEntitys;
import com.is.entitys.StoneStyletProjectileEntity;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;





public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(

                Commands.literal("attack1")

                        .then(Commands.argument("target", EntityArgument.entity())

                                .executes(ModCommands::attack1)

                        ));



        dispatcher.register(

                Commands.literal("attack2")

                        .then(Commands.argument("target", EntityArgument.entity())

                                .executes(ModCommands::attack2)

                        ));


    }

    private static int attack1(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity target = EntityArgument.getEntity(context, "target");
        StoneStyletProjectileEntity projectile = new StoneStyletProjectileEntity(ModEntitys.STONE_STYLET_PROJECTILE.get(), target.getLevel());
        projectile.setOwner(target);
        projectile.setPos(target.getX(), target.getEyeY() - 0.1, target.getZ());

        Vec3 look = target.getLookAngle();
        projectile.shoot(look.x, look.y, look.z, 1.5F, 0.5F);

        target.getLevel().addFreshEntity(projectile);
        return Command.SINGLE_SUCCESS;
    }
    private static int attack2(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        Entity target = EntityArgument.getEntity(context, "target");
        if (!(target instanceof LivingEntity)) return 0;
        float radius = 3.0f;
        List<LivingEntity> entities = target.getLevel().getEntitiesOfClass(LivingEntity.class,
                new AABB(target.getX() - radius, target.getY() - radius, target.getZ() - radius,
                        target.getX() + radius, target.getY() + radius, target.getZ() + radius),
                e -> e != target && target.distanceToSqr(e) <= radius * radius);

        for (LivingEntity entity : entities) {
            entity.hurt(DamageSource.mobAttack(((LivingEntity) target)), 5.0F);
            Vec3 knockback = new Vec3(target.getX() - target.getX(),0,target.getZ() - target.getZ()).normalize().scale(0.5);
            entity.setDeltaMovement(knockback.x, 0.3, knockback.z);
        }



        return Command.SINGLE_SUCCESS;

    }

}