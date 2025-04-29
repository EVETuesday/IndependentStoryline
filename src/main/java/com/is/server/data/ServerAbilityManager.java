package com.is.server.data;

import com.is.ISConst;
import com.is.capabilities.ModCapabilities;
import com.is.capabilities.abilities.IAbilityCapability;
import com.is.data.DelphiAbilityType;
import com.is.data.DelphiItemType;
import com.is.data.IAbilityManager;
import com.is.events.DelphiBalanceChangedEvent;
import com.is.network.NetworkHandler;
import com.is.network.packets.S2CPlayerGotDelphiItemPacket;
import com.is.network.packets.S2CSyncAbilitiesPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerAbilityManager implements IAbilityManager {

    private static ServerAbilityManager manager;

    public static void initialize() {
        manager = new ServerAbilityManager();
    }

    public static ServerAbilityManager getInstance() {
        return manager;
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private ServerAbilityManager() {
    }

    @Override
    public void addAbility(Player player, DelphiAbilityType abilityType) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return;

        capability.addAbility(abilityType);
        abilityType.ability.add((ServerPlayer) player);
        syncPlayer((ServerPlayer) player);
    }

    @Override
    public void removeAbility(Player player, DelphiAbilityType abilityType) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return;

        capability.addAbility(abilityType);
        abilityType.ability.add((ServerPlayer) player);
        syncPlayer((ServerPlayer) player);
    }

    @Override
    public List<DelphiAbilityType> getAbilities(Player player) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return List.of();

        return capability.getAbilities();
    }

    @Override
    public void setAbilities(Player player, List<DelphiAbilityType> abilities) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return;

        capability.setAbilities(abilities);
        syncPlayer((ServerPlayer) player);
    }

    @Override
    public void setCurrentItem(Player player, DelphiItemType item) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return;

        capability.setCurrentItem(item);
    }

    @Override
    public DelphiItemType getCurrentItem(Player player) {
        IAbilityCapability capability = getCap((ServerPlayer) player);
        if (capability == null) return DelphiItemType.CHARM_OF_LUCK;

        return capability.getCurrentItem();
    }

    public void syncPlayer(ServerPlayer player) {
        syncPlayer(player, getAbilities(player), getCurrentItem(player));
    }

    protected void syncPlayer(ServerPlayer player, List<DelphiAbilityType> abilities, DelphiItemType currentItem) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new S2CSyncAbilitiesPacket(abilities, currentItem));
    }

    protected @Nullable IAbilityCapability getCap(ServerPlayer player) {
        Optional<IAbilityCapability> capability = player.getCapability(ModCapabilities.ABILITIES).resolve();
        if (capability.isPresent()) {
            return capability.get();
        }
        LOGGER.warn("No capability attached!");
        return null;
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        event.getServer().getPlayerList().getPlayers().forEach(player ->
                getInstance().getAbilities(player).forEach(type ->
                        type.ability.tick(player))
        );
    }

    @SubscribeEvent
    public static void onPlayersBalanceChanged(DelphiBalanceChangedEvent event) {
        if (event.player instanceof ServerPlayer) {
            DelphiItemType currentItem = getInstance().getCurrentItem(event.player);
            LOGGER.debug("Current item {}", currentItem);
            if (currentItem == DelphiItemType.NULL) return;
            if (ServerDelphiManager.getInstance().getNetworth(event.player) >= currentItem.requiredDelphies) {
                getInstance().setCurrentItem(event.player, DelphiItemType.values()[currentItem.ordinal() + 1]);
                event.player.addItem(currentItem.item.get());
                event.player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 20, true, false));
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new S2CPlayerGotDelphiItemPacket(currentItem));
                LOGGER.debug("Giving player {}", currentItem);
                getInstance().syncPlayer((ServerPlayer) event.player);
            }
        }
    }
}
