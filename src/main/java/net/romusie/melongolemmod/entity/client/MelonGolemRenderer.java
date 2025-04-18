package net.romusie.melongolemmod.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;

public class MelonGolemRenderer extends MobEntityRenderer<MelonGolemEntity, MelonGolemModel<MelonGolemEntity>> {


    public MelonGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new MelonGolemModel<>(context.getPart(MelonGolemModel.PIECE2)), 0.75f);
    }

    @Override
    public Identifier getTexture(MelonGolemEntity entity) {
        return Identifier.of(MelonGolemMod.MOD_ID, "textures/entity/melon_golem/melon_golem.png");
    }

    @Override
    public void render(MelonGolemEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
