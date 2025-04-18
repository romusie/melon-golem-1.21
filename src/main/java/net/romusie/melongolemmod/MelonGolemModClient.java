package net.romusie.melongolemmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.romusie.melongolemmod.entity.ModEntities;
import net.romusie.melongolemmod.entity.client.MelonGolemModel;
import net.romusie.melongolemmod.entity.client.MelonGolemRenderer;
import net.romusie.melongolemmod.entity.client.MelonSeedProjectileModel;
import net.romusie.melongolemmod.entity.client.MelonSeedProjectileModel;
import net.romusie.melongolemmod.entity.client.MelonSeedProjectileRenderer;

public class MelonGolemModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(MelonGolemModel.PIECE2, MelonGolemModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MELON_GOLEM, MelonGolemRenderer::new);


        EntityModelLayerRegistry.registerModelLayer(MelonSeedProjectileModel.MELONSEED, MelonSeedProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MELONSEED, MelonSeedProjectileRenderer::new);

    }
}
