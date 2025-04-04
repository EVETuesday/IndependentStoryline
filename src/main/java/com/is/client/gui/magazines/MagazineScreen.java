package com.is.client.gui.magazines;

import com.is.client.gui.utils.AnimationTimeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class MagazineScreen extends Screen {

    private final MagazineType magazineType;

    protected int leftPos;
    protected int topPos;

    protected final AnimationTimeline openAnimation;


    public MagazineScreen(MagazineType magazineType) {
        super(Component.empty());
        Minecraft.getInstance().player.playSound(SoundEvents.BOOK_PAGE_TURN);
        this.magazineType = magazineType;
        this.openAnimation = new AnimationTimeline(250) {
            @Override
            protected boolean shouldIncrease() {
                return true;
            }
        };
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.getMagazineType().textureWidth) / 2;
        this.topPos = (this.height - this.getMagazineType().textureHeight) / 2;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        RenderSystem.setShaderTexture(0, getMagazineType().texturePath);

        //screen bgr
        poseStack.pushPose();
        poseStack.scale(getMagazineType().scaleFactor, getMagazineType().scaleFactor, 1.0f);
        int x = (int) (((this.width - this.getMagazineType().textureWidth * getMagazineType().scaleFactor) / 2.0f) / getMagazineType().scaleFactor);
        int y = (int) (((this.height - this.getMagazineType().textureHeight * getMagazineType().scaleFactor) / 2.0f) / getMagazineType().scaleFactor);

        blit(
                poseStack,
                x,
                y,
                0,
                0,
                this.getMagazineType().textureWidth,
                this.getMagazineType().textureHeight,
                this.getMagazineType().textureWidth,
                this.getMagazineType().textureHeight
        );

        poseStack.scale(1 / getMagazineType().scaleFactor, 1 / getMagazineType().scaleFactor, 1.0f);
        poseStack.popPose();
    }

    public MagazineType getMagazineType() {
        return magazineType;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
