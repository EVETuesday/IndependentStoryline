package com.is.client.gui.utils;

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

}
