package com.EveTuPart.Tabs;

import com.is.ISConst;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTab extends CreativeModeTab {
    public ModCreativeTab() {
        super(CreativeModeTab.TABS.length, "IndependentStorylineTab");
    }

    @Override
    public ItemStack makeIcon() {
        //return new ItemStack(ModBlock.Green_Block.get());
        return new ItemStack(Items.DIAMOND);
    }
}
