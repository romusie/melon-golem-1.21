package net.romusie.melongolemmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;

public class MelonGolemModel<T extends MelonGolemEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer PIECE2 = new EntityModelLayer(Identifier.of(MelonGolemMod.MOD_ID, "melongolem"), "main");

    private final ModelPart piece2;
    private final ModelPart piece1;
    private final ModelPart head;
    private final ModelPart pumpkin;
    private final ModelPart arm1;
    private final ModelPart arm2;
    public MelonGolemModel(ModelPart root) {
        this.piece2 = root.getChild("piece2");
        this.piece1 = this.piece2.getChild("piece1");
        this.head = this.piece1.getChild("head");
        this.pumpkin = this.head.getChild("pumpkin");
        this.arm1 = this.piece1.getChild("arm1");
        this.arm2 = this.piece1.getChild("arm2");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData piece2 = modelPartData.addChild("piece2", ModelPartBuilder.create().uv(0, 36).cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData piece1 = piece2.addChild("piece1", ModelPartBuilder.create().uv(0, 16).cuboid(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, -11.0F, 0.0F));

        ModelPartData head = piece1.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, -9.0F, 0.0F));

        ModelPartData pumpkin = head.addChild("pumpkin", ModelPartBuilder.create().uv(0, 64).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(-3.0F)), ModelTransform.pivot(0.0F, -5.5F, 0.0F));

        ModelPartData arm1 = piece1.addChild("arm1", ModelPartBuilder.create().uv(0, 60).cuboid(1.0F, -4.0F, -1.0F, 12.0F, 2.0F, 2.0F, new Dilation(-0.5F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, 1.0001F));

        ModelPartData arm2 = piece1.addChild("arm2", ModelPartBuilder.create().uv(0, 60).mirrored().cuboid(-13.0F, -4.0F, -1.0F, 12.0F, 2.0F, 2.0F, new Dilation(-0.5F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.0001F));
        return TexturedModelData.of(modelData, 64, 96);
    }
    @Override
    public void setAngles(MelonGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);



    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        piece2.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return piece2;
    }
}

