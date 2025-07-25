package com.is.client.data;

import com.is.client.event_listeners.ClientEventListener;
import com.is.client.gui.AbilitiesShopScreen;
import com.is.data.DelphiAbilityType;
import com.is.data.DelphiItemType;
import com.is.data.IAbilityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientAbilityManager implements IAbilityManager {

    private static ClientAbilityManager manager;

    public static void invalidate() {
        manager = null;
    }

    public static void initialize() {
        manager = new ClientAbilityManager();
    }

    public static ClientAbilityManager getInstance() {
        return manager;
    }

    private ClientAbilityManager() {
    }

    protected List<DelphiAbilityType> abilities = new ArrayList<>();
    protected DelphiItemType currentItem = DelphiItemType.CHARM_OF_LUCK;

    @Override
    public void addAbility(Player player, DelphiAbilityType abilityType) {
        abilities.add(abilityType);
    }

    @Override
    public void removeAbility(Player player, DelphiAbilityType abilityType) {
        abilities.remove(abilityType);
    }

    @Override
    public List<DelphiAbilityType> getAbilities(Player player) {
        return abilities;
    }

    @Override
    public void setAbilities(Player player, List<DelphiAbilityType> abilities) {
        this.abilities = abilities;
    }

    @Override
    public void setCurrentItem(Player player, DelphiItemType item) {
        currentItem = item;
        ClientEventListener.updateItemDelphiData();
    }

    @Override
    public DelphiItemType getCurrentItem(Player player) {
        return currentItem;
    }

    public void onPlayerGotItem(DelphiItemType item) {
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;

        if (level == null || player == null) return;

        Vec3 pos = player.position();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, pos.x + random.nextDouble() - 0.5, pos.y + random.nextDouble() - 0.5, pos.z + random.nextDouble() - 0.5, 0.1, 0.1, 0.1);
        }

        Minecraft.getInstance().player.sendSystemMessage(item.message.apply(Minecraft.getInstance().player));
        Minecraft.getInstance().gameRenderer.displayItemActivation(item.item.get());
        Minecraft.getInstance().gui.setTitle(Component.literal(" "));
        Minecraft.getInstance().gui.setSubtitle(Component.literal("Получен новый предмет").withStyle(Style.EMPTY.withColor(AbilitiesShopScreen.BASE_COLOR)));
        player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE);
        ClientEventListener.updateItemDelphiData();

    }
}
