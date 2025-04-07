package com.is.data;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.function.Function;

public enum DelphiForBlocks {

    COAL_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_COAL), new ItemStack(Items.COAL), 0.5d, "is.gui.delphi_shop.filters.coal"),
    COPPER_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_COPPER), new ItemStack(Items.COPPER_INGOT), 1.0d, "is.gui.delphi_shop.filters.copper"),
    IRON_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_IRON), new ItemStack(Items.IRON_INGOT), 2.0d, "is.gui.delphi_shop.filters.iron"),
    GOLD_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_GOLD), new ItemStack(Items.GOLD_INGOT), 4.0d, "is.gui.delphi_shop.filters.gold"),
    DIAMOND_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_DIAMOND), new ItemStack(Items.DIAMOND), 7.0d, "is.gui.delphi_shop.filters.diamond"),
    EMERALD_FILTER((block) -> block.defaultBlockState().is(Tags.Blocks.ORES_EMERALD), new ItemStack(Items.EMERALD), 20.0d, "is.gui.delphi_shop.filters.emerald");

    public final Function<Block, Boolean> acceptFunc;
    public final ItemStack icon;
    public final double income;
    public final String translateName;

    DelphiForBlocks(Function<Block, Boolean> acceptFunc, ItemStack icon, double income, String translateName) {
        this.acceptFunc = acceptFunc;
        this.icon = icon;
        this.income = income;
        this.translateName = translateName;
    }

}
