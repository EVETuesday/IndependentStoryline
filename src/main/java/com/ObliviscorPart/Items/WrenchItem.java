package com.ObliviscorPart.Items;

import com.is.ISConst;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class WrenchItem extends Item {
    public WrenchItem() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(1).tab(ISConst.modCreativeTab));
    }
}
