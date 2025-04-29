package com.is.client.data;

import com.is.client.gui.utils.AnimationTimeline;
import com.is.client.gui.utils.GuiUtils;
import com.is.data.CommonEnhancedBossEvent;
import com.is.network.packets.S2CSyncEnhancedBossBarPacket;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.slf4j.Logger;

import java.util.UUID;

public class ClientEnhancedBossEvent extends CommonEnhancedBossEvent {

    private static final Logger LOGGER = LogUtils.getLogger();

    protected AnimationTimeline damageAnimation = new AnimationTimeline(200) {
        @Override
        protected boolean shouldIncrease() {
            return lastValue != getValue() && fadeInOutAnimation.isEnded();
        }
    };

    protected AnimationTimeline fadeInOutAnimation = new AnimationTimeline(250) {
        @Override
        protected boolean shouldIncrease() {
            return true;
        }
    };

    private float lastValue = 0.0f;
    private boolean markedRemoved = false;

    protected final int imageWidth;
    protected final int imageHeight;
    protected final int bossBarHeight;

    private final ResourceLocation texture;
    private final boolean shakeOnChange;

    public ClientEnhancedBossEvent(UUID pId, ResourceLocation texture, Component pName, float maxValue, boolean pDarkenScreen, boolean pBossMusic, boolean pWorldFog) {
        this(pId, texture, pName, maxValue, pDarkenScreen, pBossMusic, pWorldFog, true);
    }

    public ClientEnhancedBossEvent(UUID pId, ResourceLocation texture, Component pName, float maxValue, boolean pDarkenScreen, boolean pBossMusic, boolean pWorldFog, boolean shakeOnChange) {
        super(pId, pName, maxValue, pDarkenScreen, pWorldFog, pBossMusic);
        this.texture = texture;
        this.shakeOnChange = shakeOnChange;
        this.fadeInOutAnimation.pause();
        Tuple<Integer, Integer> textureSize = GuiUtils.getTextureSize(texture);
        this.imageWidth = textureSize.getA();
        this.imageHeight = textureSize.getB();
        this.bossBarHeight = imageHeight / 3;
        if (this.imageWidth == -1) {
            LOGGER.error("Invalid texture size!");
        }
    }

    public void render(PoseStack poseStack, int y) {
        damageAnimation.update();
        fadeInOutAnimation.update();

        if (markedRemoved && fadeInOutAnimation.isEnded()) {
            ClientEnhancedBossEventManager.getInstance().remove(getId());
            return;
        }

        float lastShakeX = 0.0f;
        float lastShakeY = 0.0f;
        if (damageAnimation.isEnded()) {
            lastValue = getValue();
            damageAnimation.reset();
        } else if (shakeOnChange) {
            lastShakeX = (float) ((Math.random() - 0.5f) * 1.35f);
            lastShakeY = (float) ((Math.random() - 0.5f) * 1.35f);
        }

        Window window = Minecraft.getInstance().getWindow();

        poseStack.pushPose();
        poseStack.translate(lastShakeX, lastShakeY, 0.0f);
        RenderSystem.setShaderTexture(0, texture);
        int x = (window.getGuiScaledWidth() - imageWidth) / 2;

        float fadeInOutPercentage = markedRemoved ? 1.0f - fadeInOutAnimation.getPercentage() : fadeInOutAnimation.getPercentage();
        if (!fadeInOutAnimation.isEnded()) {
            RenderSystem.setShaderColor(fadeInOutPercentage, fadeInOutPercentage, 1.0f, fadeInOutPercentage);
            GuiComponent.enableScissor((int) (x + imageWidth / 2.0f * (1 - fadeInOutPercentage)), y, (int) (x + imageWidth / 2.0f + imageWidth / 2.0f * fadeInOutPercentage), y + bossBarHeight);
        }

        // background
        if (fadeInOutAnimation.isEnded()) {
            if (getValue() > lastValue) {
                GuiComponent.enableScissor((int) (x + imageWidth * (lastValue / getMaxValue())), y, x + imageWidth, y + bossBarHeight);
            } else {
                GuiComponent.enableScissor((int) (x + imageWidth * getProgress()), y, x + imageWidth, y + bossBarHeight);
            }
        }
        GuiComponent.blit(poseStack, x, y, 0, bossBarHeight, imageWidth, bossBarHeight, imageWidth, imageHeight);
        if (fadeInOutAnimation.isEnded()) {
            GuiComponent.disableScissor();
        }

        // filled thing
        if (getValue() >= lastValue) {
            GuiComponent.blit(poseStack, x, y, 0, 0, (int) (imageWidth * (lastValue / getMaxValue())), bossBarHeight, imageWidth, imageHeight);
        } else {
            GuiComponent.blit(poseStack, x, y, 0, 0, (int) (imageWidth * getProgress()), bossBarHeight, imageWidth, imageHeight);
        }

        // animation
        if (fadeInOutAnimation.isEnded()) {
            if (getValue() > lastValue) {
                GuiComponent.enableScissor((int) (x + imageWidth * (lastValue / getMaxValue())), y, (int) (x + (imageWidth + 1) * (lastValue / getMaxValue()) + (damageAnimation.getPercentage() * imageWidth * (Math.abs(getValue() - lastValue) / getMaxValue()))), y + bossBarHeight);
            } else {
                GuiComponent.enableScissor((int) (x + imageWidth * getProgress()), y, (int) (x + imageWidth * getProgress() + ((1.0f - damageAnimation.getPercentage()) * imageWidth * (Math.abs(getValue() - lastValue) / getMaxValue()))), y + bossBarHeight);
            }
            GuiComponent.blit(poseStack, x, y, 0, bossBarHeight * 2, imageWidth, bossBarHeight, imageWidth, imageHeight);
            GuiComponent.disableScissor();
        }

        if (!fadeInOutAnimation.isEnded()) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.disableScissor();
        }

        poseStack.popPose();
    }

    public void sync(S2CSyncEnhancedBossBarPacket packet) {
        setValue(packet.value);
        setMaxValue(packet.maxValue);
        if (packet.removed) {
            ClientEnhancedBossEventManager.getInstance().markRemoved(getId());
        }
    }

    @Override
    public void remove() {
        markedRemoved = true;
        fadeInOutAnimation.resume();
        fadeInOutAnimation.reset();
    }

    @Override
    public void add() {
        fadeInOutAnimation.resume();
    }

    @Override
    protected void syncClients(boolean removed) {
    }

    @Override
    public void setValue(float value) {
        super.setValue(value);
        damageAnimation.reset();
    }

    public int getBossBarHeight() {
        return bossBarHeight;
    }
}
