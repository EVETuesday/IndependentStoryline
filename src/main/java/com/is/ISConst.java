package com.is;

import com.is.data.DelphiForBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public final class ISConst {

    private ISConst() {}

    public static final String MODID = "independent_storyline";

    public static final boolean DEBUG = true;

    public static ResourceLocation rl(String subPath) {
        return ResourceLocation.fromNamespaceAndPath(MODID, subPath);
    }

    public static double getDelphiByBlock(Block block) {
        for (DelphiForBlocks delphiForBlocks: DelphiForBlocks.values()) {
            if (delphiForBlocks.acceptFunc.apply(block)) {
                return delphiForBlocks.income;
            }
        }
        return 0.0d;
    }

}
