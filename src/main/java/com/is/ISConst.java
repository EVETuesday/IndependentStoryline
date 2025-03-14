package com.is;

import com.is.server.data.ServerDelphiManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public final class ISConst {

    public static final String MODID = "independent_storyline";
    public static final boolean DEBUG = false;
    public static final Map<Block, Double> DELPHI_BY_BLOCKS = new HashMap<>() {{
    }};

    public static ResourceLocation rl(String subPath) {
        return ResourceLocation.fromNamespaceAndPath(MODID, subPath);
    }

    public static ServerDelphiManager getServerDelphiManager() {
        return ServerDelphiManager.getInstance();
    }

    public static int getDelphiByBlock(Block block) {
        throw new NotImplementedException();
    }

}
