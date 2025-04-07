package com.is.client.gui;

import com.is.ISConst;
import com.is.client.data.ClientAbilityManager;
import com.is.client.data.ClientDelphiManager;
import com.is.client.gui.utils.AnimationTimeline;
import com.is.client.gui.utils.GuiUtils;
import com.is.data.DelphiAbilityType;
import com.is.data.DelphiForBlocks;
import com.is.network.NetworkHandler;
import com.is.network.packets.C2SBuyAbilityPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesShopScreen extends Screen {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation BG_TEXTURE = ISConst.rl("textures/gui/delphi_upgrade_shop.png");
    public static ResourceLocation DELPHI_COIN_TEXTURE = ISConst.rl("textures/gui/delphi_coin.png");

    protected static final int BUTTONS_AREA_START_Y = 82;
    protected static final int BUTTONS_AREA_START_X = 182;
    protected static final int BUTTONS_AREA_HEIGHT = 145;

    protected static final int BUTTON_WIDTH = 127;
    protected static final int BUTTON_HEIGHT = 35;

    protected static final int DESCRIPTION_AREA_START_Y = 90;
    protected static final int DESCRIPTION_AREA_START_X = 30;
    protected static final int DESCRIPTION_AREA_WIDTH = 135;
    protected static final int DESCRIPTION_AREA_HEIGHT = 145;

    protected static final int SCREEN_LABEL_AREA_START_X = 108;
    protected static final int SCREEN_LABEL_AREA_START_Y = 30;
    protected static final int SCREEN_LABEL_AREA_WIDTH = 135;
    protected static final int SCREEN_LABEL_AREA_HEIGHT = 32;

    public static final int BASE_COLOR = 0xFF76BAC9;

    protected int imageWidth = 350;
    protected int imageHeight = 256;
    protected int leftPos;
    protected int topPos;

    protected List<UpgradeButton> buttons = new ArrayList<>();

    @Nullable
    protected UpgradeButton selectedUpgradeButton = null;

    public AbilitiesShopScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        this.clearWidgets();
        buttons.clear();

        this.selectedUpgradeButton = null;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        //upgrade buttons
        {
            int gap = (BUTTONS_AREA_HEIGHT - BUTTON_HEIGHT * DelphiAbilityType.values().length) / (DelphiAbilityType.values().length + 2);
            int y = BUTTONS_AREA_START_Y + gap;

            for (DelphiAbilityType upgradeType : DelphiAbilityType.values()) {
                UpgradeButton upgradeButton = new UpgradeButton(
                        BUTTONS_AREA_START_X + this.leftPos, this.topPos + y,
                        BUTTON_WIDTH, BUTTON_HEIGHT, upgradeType
                );
                y += gap + BUTTON_HEIGHT;

                if (ClientDelphiManager.getInstance().getBalance(null) < upgradeType.price) {
                    upgradeButton.setEnabled(false);
                }
                this.addRenderableWidget(upgradeButton);
                buttons.add(upgradeButton);
            }
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);

        //preparing
        RenderSystem.setShaderTexture(0, AbilitiesShopScreen.BG_TEXTURE);

        //screen bgr
        blit(poseStack, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2,
                0, 0,
                this.imageWidth, this.imageHeight,
                512, 512
        );

        { //upper label
            float scale = 1.5f;
            poseStack.pushPose();
            poseStack.scale(scale, scale, 1.0F);
            String name = I18n.get("is.gui.delphi_shop.name");
            font.draw(poseStack, name,
                    (this.leftPos + SCREEN_LABEL_AREA_START_X + (SCREEN_LABEL_AREA_WIDTH - font.width(name) * scale) / 2.0f) / scale,
                    (this.topPos + SCREEN_LABEL_AREA_START_Y - (font.lineHeight - SCREEN_LABEL_AREA_HEIGHT) / 2.0f) / scale, BASE_COLOR
            );
            poseStack.scale(1 / scale, 1 / scale, 1.0F);
            poseStack.popPose();
        }

        { //description
            String value = I18n.get("is.gui.delphi_shop.button.balance", String.valueOf(ClientDelphiManager.getInstance().getBalance(null)));
            font.draw(poseStack, value,
                    leftPos + DESCRIPTION_AREA_START_X - 2,
                    topPos + DESCRIPTION_AREA_START_Y - 20 + font.lineHeight / 2.0f, BASE_COLOR
            );

            RenderSystem.setShaderTexture(0, AbilitiesShopScreen.DELPHI_COIN_TEXTURE);
            blit(poseStack, leftPos + DESCRIPTION_AREA_START_X - 2 + Minecraft.getInstance().font.width(value) + 1, (int) (topPos + DESCRIPTION_AREA_START_Y - 20 + font.lineHeight / 2.0f),
                    0, 0,
                    10, 10,
                    10, 10
            );
            RenderSystem.setShaderTexture(0, AbilitiesShopScreen.BG_TEXTURE);

            if (selectedUpgradeButton != null) {
                List<FormattedCharSequence> description = font.split(FormattedText.of(I18n.get(selectedUpgradeButton.upgradeType.descriptionTranslation)), DESCRIPTION_AREA_WIDTH);
                float y = topPos + DESCRIPTION_AREA_START_Y;
                for (FormattedCharSequence charSequence : description) {
                    font.drawShadow(poseStack, charSequence, leftPos + DESCRIPTION_AREA_START_X, y += font.lineHeight, 0xFFFFFFFF);
                }

            } else {
                int y = topPos + DESCRIPTION_AREA_START_Y;
                for (DelphiForBlocks delphiForBlocks : DelphiForBlocks.values()) {
                    RenderSystem.setShaderTexture(0, AbilitiesShopScreen.BG_TEXTURE);
                    blit(poseStack, leftPos + DESCRIPTION_AREA_START_X - 2, y - 2,
                            106, 257,
                            20, 20,
                            512, 512
                    );

                    Minecraft.getInstance().getItemRenderer().renderGuiItem(delphiForBlocks.icon, leftPos + DESCRIPTION_AREA_START_X, y);

                    font.draw(poseStack, String.valueOf(delphiForBlocks.income),
                            leftPos + DESCRIPTION_AREA_START_X + DESCRIPTION_AREA_WIDTH - font.width(String.valueOf(delphiForBlocks.income)) - 9,
                            y + font.lineHeight / 2.0f, BASE_COLOR
                    );
                    font.draw(poseStack, I18n.get(delphiForBlocks.translateName),
                            leftPos + DESCRIPTION_AREA_START_X + 20.3f, y + font.lineHeight / 2.0f, BASE_COLOR
                    );

                    RenderSystem.setShaderTexture(0, AbilitiesShopScreen.DELPHI_COIN_TEXTURE);
                    blit(poseStack, leftPos + DESCRIPTION_AREA_START_X + DESCRIPTION_AREA_WIDTH - 8, y + 4,
                            0, 0,
                            10, 10,
                            10, 10
                    );

                    y += 22;
                }
            }
        }
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        selectedUpgradeButton = null;
        buttons.forEach(btn -> {
            if (btn.isHoveredIgnoreAll()) {
                selectedUpgradeButton = btn;
            }
        });
    }

    protected class UpgradeButton extends AbstractButton {

        protected static final int NAME_LABEL_BGR_XUV_OFFSET = 1;
        protected static final int NAME_LABEL_BGR_YUV_OFFSET = 292;
        protected static final int NAME_LABEL_PURCHASED_BGR_XUV_OFFSET = 91;
        protected static final int NAME_LABEL_PURCHASED_BGR_YUV_OFFSET = 292;
        protected static final int NAME_LABEL_BGR_WIDTH = 89;
        protected static final int NAME_LABEL_BGR_HEIGHT = 35;
        protected static final int ICON_SIZE = 34;
        protected static final int GAP_BETWEEN_ICON_AND_LABEL = 3;

        protected final DelphiAbilityType upgradeType;
        protected final AnimationTimeline animationTimeline;

        protected boolean hovered = false;
        protected boolean purchased;

        public UpgradeButton(int x, int y, int width, int height, DelphiAbilityType upgradeType) {
            super(x, y, width, height, Component.empty());
            this.upgradeType = upgradeType;
            this.animationTimeline = new AnimationTimeline(200) {
                @Override
                protected boolean shouldIncrease() {
                    return isHovered();
                }
            };
            this.purchased = ClientAbilityManager.getInstance().getAbilities(null).contains(upgradeType);
            this.active = !purchased;
        }

        @Override
        public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            this.animationTimeline.update();
            this.hovered = mouseX >= (double) this.x && mouseY >= (double) this.y && mouseX < (double) (this.x + this.width) && mouseY < (double) (this.y + this.height);

            //preparing
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, AbilitiesShopScreen.BG_TEXTURE);

            poseStack.pushPose();

            poseStack.translate(3.0F * this.animationTimeline.getPercentage(), 0.0F, 0.0F);
            float mulColor = 1.0F;

            if (this.purchased) {
                mulColor = 0.7F;
                RenderSystem.setShaderColor(mulColor, mulColor, mulColor, 1.0F);
            }

            fill(poseStack, this.x, this.y + NAME_LABEL_BGR_HEIGHT + 1,
                    (int) (this.x + AbilitiesShopScreen.BUTTON_WIDTH * this.animationTimeline.getPercentage()), this.y + NAME_LABEL_BGR_HEIGHT + 2, BASE_COLOR
            );

            //name label background
            if (!purchased) {
                blit(poseStack, this.x, this.y,
                        NAME_LABEL_BGR_XUV_OFFSET, NAME_LABEL_BGR_YUV_OFFSET,
                        NAME_LABEL_BGR_WIDTH, NAME_LABEL_BGR_HEIGHT,
                        512, 512
                );
            } else {
                blit(poseStack, this.x, this.y,
                        NAME_LABEL_PURCHASED_BGR_XUV_OFFSET, NAME_LABEL_PURCHASED_BGR_YUV_OFFSET,
                        NAME_LABEL_BGR_WIDTH, NAME_LABEL_BGR_HEIGHT,
                        512, 512
                );
            }

            //icon
            blit(poseStack, this.x + GAP_BETWEEN_ICON_AND_LABEL + NAME_LABEL_BGR_WIDTH, this.y,
                    upgradeType.xUVOffset, upgradeType.yUVOffset,
                    ICON_SIZE, ICON_SIZE,
                    512, 512
            );

            Font font = Minecraft.getInstance().font;
            String name = I18n.get(upgradeType.nameTranslation);

            float textY = this.y - 4.0f;
            float textX = this.x + 2.0f;
            float scale = 0.8f;

            poseStack.scale(scale, scale, 1.0F);
            font.draw(poseStack, name, textX / scale, textY / scale, GuiUtils.mulColorRGB(BASE_COLOR, mulColor));
            poseStack.scale(1.0F / scale, 1.0F / scale, 1.0F);

            textY = this.y + (NAME_LABEL_BGR_HEIGHT - font.lineHeight) / 2.0f;
            textX = this.x + 5.0f;
            scale = 1.4f;
            poseStack.scale(scale, scale, 1.0F);
            if (!purchased) {
                font.draw(poseStack, String.valueOf(upgradeType.price),
                        textX / scale, textY / scale,
                        GuiUtils.mulColorRGB(this.active ? GuiUtils.mulColorRGB(BASE_COLOR, 0.7f) : 0xFFFF0000, mulColor)
                );
                RenderSystem.setShaderTexture(0, AbilitiesShopScreen.DELPHI_COIN_TEXTURE);
                blit(poseStack, (int) (textX / scale + font.width(String.valueOf(upgradeType.price)) + 2), (int) ((textY - 2) / scale),
                        0, 0,
                        10, 10,
                        10, 10
                );
            } else {
                font.draw(poseStack, I18n.get("is.gui.delphi_shop.button.purchased"),
                        textX / scale, textY / scale,
                        GuiUtils.mulColorRGB(GuiUtils.mulColorRGB(BASE_COLOR, 0.7f), mulColor)
                );
            }
            poseStack.scale(1.0F / scale, 1.0F / scale, 1.0F);

            poseStack.popPose();

            if (isHoveredIgnoreAll() && !isHovered()) {
                if (purchased) {
                    AbilitiesShopScreen.this.renderTooltip(poseStack, Component.translatable("is.gui.delphi_shop.button.exception.already_purchased"), mouseX, mouseY);
                } else {
                    AbilitiesShopScreen.this.renderTooltip(poseStack, Component.translatable("is.gui.delphi_shop.button.exception.not_enough_delphi"), mouseX, mouseY);
                }
            }
        }

        @Override
        public void onPress() {
            NetworkHandler.INSTANCE.sendToServer(new C2SBuyAbilityPacket(upgradeType));
        }

        @Override
        public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {

        }

        public void setEnabled(boolean enabled) {
            this.active = enabled;
        }

        public boolean isHovered() {
            return hovered && active;
        }

        public boolean isHoveredIgnoreAll() {
            return hovered;
        }
    }
}
