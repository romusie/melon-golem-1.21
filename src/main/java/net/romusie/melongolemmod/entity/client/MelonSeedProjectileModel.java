package net.romusie.melongolemmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.entity.custom.MelonSeedProjectileEntity;

public class MelonSeedProjectileModel extends EntityModel<MelonSeedProjectileEntity> {
    public static final EntityModelLayer MELONSEED = new EntityModelLayer(Identifier.of(MelonGolemMod.MOD_ID, "melonseed"), "main");
    private final ModelPart melonseed;

    public MelonSeedProjectileModel(ModelPart root) {
        this.melonseed = root.getChild("melonseed");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData melonseed = modelPartData.addChild("melonseed", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 10.0F, -1.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(MelonSeedProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        melonseed.render(matrices, vertexConsumer, light, overlay, color);
    }
}