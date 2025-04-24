package com.is.client.gui.magazines;

import com.is.ISConst;
import net.minecraft.resources.ResourceLocation;

public enum MagazineType {

    MAGAZINE_1(ISConst.rl("textures/gui/magazines/magazine_1.png"), 3200, 3840, 0.06f),
    MAGAZINE_2(ISConst.rl("textures/gui/magazines/magazine_2.png"), 3200, 3840, 0.06f);

    public final ResourceLocation texturePath;
    public final int textureWidth;
    public final int textureHeight;
    public final float scaleFactor;

    MagazineType(ResourceLocation texturePath, int textureWidth, int textureHeight, float scaleFactor) {
        this.texturePath = texturePath;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.scaleFactor = scaleFactor;
    }

}
