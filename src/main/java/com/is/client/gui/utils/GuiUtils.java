package com.is.client.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;

import java.io.IOException;

public final class GuiUtils {

    public static int calculateMiddleColorForAnim(int startColor, int endColor, AnimationTimeline animationTimeline) {
        int calcR = startColor & 255;
        calcR = (int) (calcR - (calcR - (endColor & 255)) * animationTimeline.getPercentage());
        int calcG = (startColor >> 8) & 255;
        calcG = (int) (calcG - (calcG - ((endColor >> 8) & 255)) * animationTimeline.getPercentage());
        int calcB = (startColor >> 16) & 255;
        calcB = (int) (calcB - (calcB - ((endColor >> 16) & 255)) * animationTimeline.getPercentage());
        int alpha = (startColor >> 24) & 255;
        alpha = (int) (alpha - (alpha - ((endColor >> 24) & 255)) * animationTimeline.getPercentage());
        return (calcR | (calcG << 8)) | (calcB << 16) | (alpha << 24);
    }

    public static int mulColorRGB(int source, float factor) {
        int r = (int) ((source & 255) * factor);
        int g = (int) (((source >> 8) & 255) * factor);
        int b = (int) (((source >> 16) & 255) * factor);
        int alpha = (source >> 24) & 255;
        return (r | (g << 8)) | (b << 16) | (alpha << 24);
    }

    public static Tuple<Integer, Integer> getTextureSize(ResourceLocation rl) {
        if (Minecraft.getInstance().getTextureManager().getTexture(rl) instanceof SimpleTexture simpleTexture) {
            SimpleTexture.TextureImage image = simpleTexture.getTextureImage(Minecraft.getInstance().getResourceManager());
            try {
                return new Tuple<>(image.getImage().getWidth(), image.getImage().getHeight());
            } catch (IOException ignored) {
            }
        }

        return new Tuple<>(-1, -1);
    }

}
