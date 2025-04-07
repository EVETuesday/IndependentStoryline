package com.is;

import com.EveTuPart.Tabs.ModCreativeTab;
import com.is.data.DelphiForBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;

public final class ISConst {

    private ISConst() {
    }

    public static final String MODID = "independent_storyline";

    public static final boolean DEBUG = !FMLEnvironment.production;

    public static ResourceLocation rl(String subPath) {
        return ResourceLocation.fromNamespaceAndPath(MODID, subPath);
    }

    public static double getDelphiByBlock(Block block) {
        for (DelphiForBlocks delphiForBlocks : DelphiForBlocks.values()) {
            if (delphiForBlocks.acceptFunc.apply(block)) {
                return delphiForBlocks.income;
            }
        }
        return 0.0d;
    }

    public static final ModCreativeTab modCreativeTab = new ModCreativeTab();

    public static List<Component> generateMagicItemDescription(ItemStack itemStack, Player player, String registryName) {
        List<Component> components = new ArrayList<>();
        if (player instanceof LocalPlayer) {
            if (Screen.hasShiftDown()) {
                components.add(Component.translatable("is.item.abilities"));
                for (int i = 1; I18n.exists("is.item." + itemStack.getItem() + ".description." + i); i++) {
                    components.add(Component.literal("  > ").withStyle(Style.EMPTY.withColor(0xFF009B02)).append(Component.translatable("is.item." + registryName + ".description." + i)));
                }
            } else {
                components.add(Component.translatable("is.item." + registryName + ".magic_description").withStyle(Style.EMPTY.withColor(0xFFAAAAAA)));
                components.add(Component.literal(" "));
                components.add(Component.translatable("is.item.shift_for_desc").withStyle(Style.EMPTY.withColor(0xFFAAAAAA).withItalic(true)));
            }
        }
        return components;
    }

}
