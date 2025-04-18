package net.romusie.melongolemmod.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.romusie.melongolemmod.MelonGolemMod;
import net.romusie.melongolemmod.item.custom.MelonSeeditem;


public class ModItems {

    public static final Item MELONSEED = registerItem("melonseed",
            (new MelonSeeditem(new Item.Settings().maxCount(16))));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MelonGolemMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MelonGolemMod.LOGGER.info("Registering Mod Items for " + MelonGolemMod.MOD_ID);
    }
}
