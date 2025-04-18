package net.romusie.melongolemmod.entity.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.entity.custom.MelonSeedProjectileEntity;

public class MelonSeedProjectileRenderer extends EntityRenderer<MelonSeedProjectileEntity> {
    protected MelonSeedProjectileModel model;

    public MelonSeedProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new MelonSeedProjectileModel(ctx.getPart(MelonSeedProjectileModel.MELONSEED));
    }

    @Override
    public void render(MelonSeedProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        matrices.translate(0, -1.0f, 0);
        matrices.scale(1.5f, 1.5f, 1.5f);

        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers,
                this.model.getLayer(Identifier.of(MelonGolemMod.MOD_ID, "textures/entity/melonseed/melonseed.png")), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }


    @Override
    public Identifier getTexture(MelonSeedProjectileEntity entity) {
        return Identifier.of(MelonGolemMod.MOD_ID, "textures/entity/melonseed/melonseed.png");
    }
}
