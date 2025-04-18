package net.romusie.melongolemmod.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;
import net.romusie.melongolemmod.entity.custom.MelonSeedProjectileEntity;

public class ModEntities {

    // Rejestracja Melon Golema
    public static final EntityType<MelonGolemEntity> MELON_GOLEM = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MelonGolemMod.MOD_ID, "melongolem_mod"),
            EntityType.Builder.create(MelonGolemEntity::new, SpawnGroup.CREATURE)
                    .allowSpawningInside(Blocks.POWDER_SNOW)
                    .dimensions(0.7F, 1.9F)
                    .eyeHeight(1.7F)
                    .maxTrackingRange(8)
                    .build("melongolem_mod")
    );

    public static final EntityType<MelonSeedProjectileEntity> MELONSEED = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(MelonGolemMod.MOD_ID, "melonseed"),
            EntityType.Builder.<MelonSeedProjectileEntity>create(MelonSeedProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).build());

    // Metoda rejestrująca wszystkie encje
    public static void registerModEntities() {
        MelonGolemMod.LOGGER.info("Registering Mod Entities for " + MelonGolemMod.MOD_ID);
    }
}
