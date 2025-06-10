package com.is.client.entity.render;

import com.is.entitys.StoneStyletProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import static com.is.ISConst.MODID;
import static com.is.client.entity.render.StoneStyletModel.STONE_STYLET_LAYER;


public class StoneStyletRender extends EntityRenderer<StoneStyletProjectileEntity> {
    private static final ResourceLocation STONE_STYLET_LOCATION = new ResourceLocation(MODID,"textures/entity/stone_stylet.png");
//    private static final ResourceLocation STONE_STYLET_LOCATION = new ResourceLocation(MODID, "textures/entity/rhino.png");
    private final StoneStyletModel<StoneStyletProjectileEntity> model;

    public StoneStyletRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new StoneStyletModel<>(pContext.bakeLayer(STONE_STYLET_LAYER));
    }

    public void render(StoneStyletProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, (double)0.15F, 0.0D);
        this.model.setupAnim(pEntity, 0, 0.0F, (float)pEntity.tickCount + pPartialTicks, 0.0F, 0.0F);
        this.model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(this.model.renderType(STONE_STYLET_LOCATION)),
                pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(StoneStyletProjectileEntity pEntity) {
        return STONE_STYLET_LOCATION;
    }
}