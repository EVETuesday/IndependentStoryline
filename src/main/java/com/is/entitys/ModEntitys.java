package com.is.entitys;

import com.is.ISConst;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntitys {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ISConst.MODID);
    public static final RegistryObject<EntityType<StoneStyletProjectileEntity>> STONE_STYLET_PROJECTILE =
            ENTITY_TYPES.register("stone_stylet", () -> EntityType.Builder.<StoneStyletProjectileEntity>of(StoneStyletProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("stone_stylet"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
