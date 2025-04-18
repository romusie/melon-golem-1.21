package net.romusie.melongolemmod.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.romusie.melongolemmod.MelonGolemMod;

public class ModBlocks {

    // Twój nowy blok "curved melon"
    public static final Block CURVED_MELON = registerBlock("curved_melon",
            new CurvedMelonBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIME)
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .allowsSpawning(Blocks::always)
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .requiresTool()));  // Możemy dodać inne opcje, jak piston behavior

    // Rejestracja bloku
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MelonGolemMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MelonGolemMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    // Wywoływane przy starcie
    public static void registerModBlocks() {
        MelonGolemMod.LOGGER.info("Registering Mod Blocks for " + MelonGolemMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries ->
                fabricItemGroupEntries.add(ModBlocks.CURVED_MELON));
    }
}
