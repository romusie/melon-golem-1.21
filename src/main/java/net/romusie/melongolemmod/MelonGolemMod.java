package net.romusie.melongolemmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.romusie.melongolemmod.block.ModBlocks;
import net.romusie.melongolemmod.entity.ModEntities;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;
import net.romusie.melongolemmod.event.MelonStripEventHandler;
import net.romusie.melongolemmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MelonGolemMod implements ModInitializer {
	public static final String MOD_ID = "melongolem-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hellow from Melon Golem Mod!");

		ModItems.registerModItems();


		ModBlocks.registerModBlocks();
		MelonStripEventHandler.register();

		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.MELON_GOLEM, MelonGolemEntity.createMelonGolemAttributes());
	}
}