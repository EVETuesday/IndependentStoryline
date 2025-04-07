package com.is.data;

import com.google.common.collect.Maps;
import com.is.ISConst;
import com.is.client.data.ClientEnhancedBossEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CommonEnhancedBossEventManager extends SavedData {

    private static CommonEnhancedBossEventManager INSTANCE;

    public static void initialize() {
        INSTANCE = new CommonEnhancedBossEventManager();
    }

    public static CommonEnhancedBossEventManager getInstance() {
        return INSTANCE;
    }

    final Map<UUID, CommonEnhancedBossEvent> bossBars = Maps.newHashMap();

    private CommonEnhancedBossEventManager() {
    }

    public static CommonEnhancedBossEventManager load(CompoundTag tag) {
        System.out.println("dksaldkasl;dkl;asfddddd " + tag);
        CommonEnhancedBossEventManager data = getInstance();
        for (String key : tag.getAllKeys()) {
            UUID id = UUID.fromString(key);
            CompoundTag bossEventTag = (CompoundTag) tag.get(key);
            if (bossEventTag == null) continue;
            CommonEnhancedBossEvent enhancedBossEvent = new CommonEnhancedBossEvent(
                    id,
                    Component.Serializer.fromJson(bossEventTag.getString("name")),
                    bossEventTag.getFloat("maxValue"),
                    bossEventTag.getBoolean("darkenScreen"),
                    bossEventTag.getBoolean("playBossMusic"),
                    bossEventTag.getBoolean("addWorldFog")
            );
            enhancedBossEvent.setValue(bossEventTag.getFloat("value"));
            data.add(enhancedBossEvent);
        }
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        System.out.println("dksaldkasl;dkl;as");
        for (CommonEnhancedBossEvent bossEvent : getAllBossBars()) {
            CompoundTag bossEventTag = new CompoundTag();
            bossEventTag.putString("name", Component.Serializer.toJson(bossEvent.getName()));
            bossEventTag.putFloat("value", bossEvent.getValue());
            bossEventTag.putFloat("maxValue", bossEvent.getMaxValue());
            bossEventTag.putBoolean("darkenScreen", bossEvent.shouldDarkenScreen());
            bossEventTag.putBoolean("playBossMusic", bossEvent.shouldPlayBossMusic());
            bossEventTag.putBoolean("addWorldFog", bossEvent.shouldCreateWorldFog());
            compoundTag.put(bossEvent.getId().toString(), bossEventTag);
        }
        return compoundTag;
    }

    public boolean add(CommonEnhancedBossEvent event) {
        setDirty();
        event.add();
        return bossBars.putIfAbsent(event.getId(), event) == null;
    }

    public boolean remove(UUID id) {
        setDirty();
        getBossBar(id).ifPresent(CommonEnhancedBossEvent::remove);
        return bossBars.remove(id) != null;
    }

    public boolean update(ClientEnhancedBossEvent event) {
        setDirty();
        return bossBars.replace(event.getId(), event) != null;
    }

    public Optional<CommonEnhancedBossEvent> getBossBar(Predicate<CommonEnhancedBossEvent> predicate) {
        setDirty();
        return bossBars.values().stream().filter(predicate).findAny();
    }

    public Optional<CommonEnhancedBossEvent> getBossBar(UUID id) {
        setDirty();
        return Optional.ofNullable(bossBars.get(id));
    }

    public List<CommonEnhancedBossEvent> getAllBossBars() {
        return bossBars.values().stream().toList();
    }

    public void syncClients() {
        getAllBossBars().forEach((bossBar) -> bossBar.syncClients(false));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityDamage(LivingDamageEvent event) {
        getInstance().updateBossBarFromEntity(event.getEntity(), Mth.clamp(event.getEntity().getHealth() - event.getAmount(), 0, event.getEntity().getMaxHealth()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void oneEntityHealth(LivingHealEvent event) {
        getInstance().updateBossBarFromEntity(event.getEntity(), Mth.clamp(event.getEntity().getHealth() + event.getAmount(), 0, event.getEntity().getMaxHealth()));
    }

    public void bindOnEntity(LivingEntity entity, CommonEnhancedBossEvent event) {
        event.setMaxValue(entity.getMaxHealth());
        event.setValue(entity.getHealth());
        add(event);
    }

    private void updateBossBarFromEntity(LivingEntity entity, float newHp) {
        Optional<CommonEnhancedBossEvent> enhancedBossEvent = getInstance().getBossBar(entity.getUUID());
        if (enhancedBossEvent.isEmpty()) return;
        enhancedBossEvent.get().setMaxValue(entity.getMaxHealth());
        enhancedBossEvent.get().setValue(newHp);
        if (newHp <= 0) {
            remove(entity.getUUID());
        } else {
            enhancedBossEvent.get().syncClients(false);
        }
    }

}
