package com.is.items;

import com.is.ISConst;
import com.is.client.gui.magazines.MagazineType;
import com.is.network.NetworkHandler;
import com.is.network.packets.S2COpenMagazinePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class MagazineItem extends Item {

    public final static CompoundTag MAGAZINE_COMPOUND_1;
    public final static CompoundTag MAGAZINE_COMPOUND_2;

    static {
        MAGAZINE_COMPOUND_1 = new CompoundTag();
        MAGAZINE_COMPOUND_1.putInt(MagazineItem.MAGAZINE_TYPE_NBT, 0);
        MAGAZINE_COMPOUND_2 = new CompoundTag();
        MAGAZINE_COMPOUND_2.putInt(MagazineItem.MAGAZINE_TYPE_NBT, 1);
    }

    public static final String MAGAZINE_TYPE_NBT = "magazineType";

    public MagazineItem() {
        super(new Properties().tab(ISConst.modCreativeTab).stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer instanceof ServerPlayer player) {
            int id = itemStack.getOrCreateTag().getInt(MAGAZINE_TYPE_NBT);
            if (id >= 0 && id < MagazineType.values().length) {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new S2COpenMagazinePacket(MagazineType.values()[id]));
                return InteractionResultHolder.success(itemStack);
            }

            return InteractionResultHolder.pass(itemStack);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, true);
    }
}
