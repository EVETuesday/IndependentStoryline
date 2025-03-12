package com.is.client.gui;

import com.is.ISConst;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public final class HotbarRenders {

    private static final ResourceLocation HOTBAR_TEXTURE = ISConst.rl("textures/gui/enhanced_hotbar.png");

    private static final int HEIGHT = 22;
    private static final int WIDTH = 242;

    private static final int UPPER_WIDTH = 184;
    private static final int UPPER_HEIGHT = 15;

    private static final int HOTBAR_X_START = 32;

    private static final int UV_BLANK_X = 81;
    private static final int UV_BLANK_Y = 41;
    private static final int UV_BLANK_SIZE = 11;

    private static final int UV_BAR_X = 23;
    private static final int UV_BAR_Y = 41;
    private static final int UV_BAR_WIDTH = 57;
    private static final int UV_BAR_HEIGHT = 7;
    private static final int UV_BAR_FILLER_Y = 50;

    public static void renderHealth(ForgeGui gui, PoseStack poseStack, float delta, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) return;
        gui.setupOverlayRenderState(true, false);
        screenWidth = gui.screenWidth;
        screenHeight = gui.screenHeight;

        if (gui.getMinecraft().getCameraEntity() instanceof Player player) {
            int x = screenWidth / 2 - UPPER_WIDTH / 2;
            int y = screenHeight - HEIGHT - UPPER_HEIGHT + 1;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, HOTBAR_TEXTURE);
            gui.blit(poseStack, x, y, 30, 1, UPPER_WIDTH, UPPER_HEIGHT);

            FoodData foodData = player.getFoodData();
            int foodLevel = foodData.getFoodLevel();
            int foodBarWidth = (int) Math.floor((double) foodLevel / 20 * UV_BAR_WIDTH);

            double playersHealth = Math.max(player.getHealth(), 0);
            double playersMaxHealth = Math.max(player.getHealth(), player.getAttributeValue(Attributes.MAX_HEALTH));
            int healthBarWidth = (int) Math.floor(playersHealth / playersMaxHealth * UV_BAR_WIDTH);

            gui.blit(poseStack, x + 6, y + UV_BAR_HEIGHT - 7, UV_BLANK_X, UV_BLANK_Y, UV_BLANK_SIZE, UV_BLANK_SIZE);
            RenderSystem.setShaderColor(0.3F, 0F, 0F, 1.0F);
            gui.blit(
                    poseStack, x + 5 + UV_BLANK_SIZE, y + UV_BAR_HEIGHT - 4,
                    UV_BAR_X, UV_BAR_Y, UV_BAR_WIDTH, UV_BAR_HEIGHT
            );
            RenderSystem.setShaderColor(1.0F, 0F, 0F, 1.0F);
            gui.blit(
                    poseStack, x + 5 + UV_BLANK_SIZE, y + UV_BAR_HEIGHT - 4,
                    UV_BAR_X, UV_BAR_FILLER_Y, healthBarWidth, UV_BAR_HEIGHT
            );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            int l1 = Mth.ceil(player.getAbsorptionAmount());
//            int i2 = Mth.ceil((f + (float) l1) / 2.0F / 10.0F);
//            int j2 = Math.max(10 - (i2 - 2), 3);
//            int k2 = k1 - (i2 - 1) * j2 - 10;
//            int l2 = k1 - 10;
//            int i3 = player.getArmorValue();
//            int j3 = -1;
//            if (player.hasEffect(MobEffects.REGENERATION)) {
//                j3 = gui.getGuiTicks() % Mth.ceil(f + 5.0F);
//            }

            gui.getMinecraft().getProfiler().push("armor");
            gui.getMinecraft().getProfiler().popPush("food");
            gui.blit(
                    poseStack, x + UPPER_WIDTH - UV_BLANK_SIZE - 6, y + UV_BAR_HEIGHT - 7,
                    UV_BLANK_X, UV_BLANK_Y, UV_BLANK_SIZE, UV_BLANK_SIZE
            );
            RenderSystem.setShaderColor(0.3F, 0.15F, 0.15F, 1.0F);
            gui.blit(
                    poseStack, x + UPPER_WIDTH - UV_BLANK_SIZE - UV_BAR_WIDTH - 5, y + UV_BAR_HEIGHT - 4,
                    UV_BAR_X, UV_BAR_Y, UV_BAR_WIDTH, UV_BAR_HEIGHT
            );
            RenderSystem.setShaderColor(0.75F, 0.5F, 0.5F, 1.0F);
            gui.blit(
                    poseStack, x + UPPER_WIDTH - UV_BLANK_SIZE - UV_BAR_WIDTH - 5, y + UV_BAR_HEIGHT - 4,
                    UV_BAR_X, UV_BAR_FILLER_Y, foodLevel, UV_BAR_HEIGHT
            );

//            int l3;
//            for(int k3 = 0; k3 < 10; ++k3) {
//                if (i3 > 0) {
//                    l3 = i1 + k3 * 8;
//                    if (k3 * 2 + 1 < i3) {
//                        gui.blit(poseStack, l3, k2, 34, 9, 9, 9);
//                    }
//
//                    if (k3 * 2 + 1 == i3) {
//                        gui.blit(poseStack, l3, k2, 25, 9, 9, 9);
//                    }
//
//                    if (k3 * 2 + 1 > i3) {
//                        gui.blit(poseStack, l3, k2, 16, 9, 9, 9);
//                    }
//                }
//            }
//
//            gui.getMinecraft().getProfiler().popPush("health");

//            int i4;
//            int j4;
//            int k4;
//            int l4;
//            int i5;
//            if (l3 == 0) {
//                gui.getMinecraft().getProfiler().popPush("food");
//
//                for(i4 = 0; i4 < 10; ++i4) {
//                    j4 = k1;
//                    k4 = 16;
//                    l4 = 0;
//                    if (player.hasEffect(MobEffects.HUNGER)) {
//                        k4 += 36;
//                        l4 = 13;
//                    }
//
//                    i5 = j1 - i4 * 8 - 9;
//                    gui.blit(poseStack, i5, j4, 16 + l4 * 9, 27, 9, 9);
//                    if (i4 * 2 + 1 < l) {
//                        gui.blit(poseStack, i5, j4, k4 + 36, 27, 9, 9);
//                    }
//
//                    if (i4 * 2 + 1 == l) {
//                        gui.blit(poseStack, i5, j4, k4 + 45, 27, 9, 9);
//                    }
//                }
//
//                l2 -= 10;
//            }

            gui.getMinecraft().getProfiler().pop();
        }
    }

    public static void renderHotbar(ForgeGui gui, PoseStack poseStack, float delta, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().options.hideGui) return;
        gui.setupOverlayRenderState(true, false);
        if (gui.getMinecraft().gameMode.getPlayerMode() == GameType.SPECTATOR) {
            gui.getSpectatorGui().renderHotbar(poseStack);
            return;
        }
        screenWidth = gui.screenWidth;
        screenHeight = gui.screenHeight;

        if (gui.getMinecraft().getCameraEntity() instanceof Player player) {
            int x = screenWidth / 2;
            int y = screenHeight / 2;

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, HOTBAR_TEXTURE);
            ItemStack itemstack = player.getOffhandItem();
            HumanoidArm humanoidarm = player.getMainArm().getOpposite();
            gui.blit(
                    poseStack, x - WIDTH / 2, screenHeight - HEIGHT,
                    1, 17, WIDTH, HEIGHT
            );
            gui.blit(
                    poseStack, x - WIDTH / 2 + HOTBAR_X_START - 1 + player.getInventory().selected * 20,
                    screenHeight - 21, 1, 41, 20, 20
            );

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            int index = 1;
            for (int slot = 0; slot < 9; ++slot) {
                gui.renderSlot(
                        x - 90 + slot * 20 + 2, screenHeight - 16 - 3,
                        delta, player, player.getInventory().items.get(slot), index++
                );
            }

            if (!itemstack.isEmpty()) {
                if (humanoidarm == HumanoidArm.LEFT) {
                    gui.renderSlot(x - WIDTH / 2 + 3, screenHeight - 16 - 3, delta, player, itemstack, index++);
                } else {
                    gui.renderSlot(x + WIDTH / 2 + 3, screenHeight - 16 - 3, delta, player, itemstack, index++);
                }
            }

            if (gui.getMinecraft().options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float f = gui.getMinecraft().player.getAttackStrengthScale(0.0F);
                if (f < 1.0F) {
                    int xc = x + 91 + 6;
                    if (humanoidarm == HumanoidArm.RIGHT) {
                        xc = x - 91 - 22;
                    }

                    RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
                    int i2 = (int) (f * 19.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.blit(poseStack, xc, screenHeight - 20, 0, 94, 18, 18);
                    gui.blit(poseStack, xc, screenHeight - 20 + 18 - i2, 18, 112 - i2, 18, i2);
                }
            }

            RenderSystem.disableBlend();
        }
    }

}
