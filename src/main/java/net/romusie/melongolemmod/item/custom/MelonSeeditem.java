package net.romusie.melongolemmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;
import net.romusie.melongolemmod.entity.custom.MelonSeedProjectileEntity;

public class MelonSeeditem extends Item {
    public MelonSeeditem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Odgrywamy dźwięk rzucania nasion
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        // Warunek sprawdzający, czy gracz nie znajduje się w trybie klienta (tylko serwer może tworzyć entity)
        if (!world.isClient) {
            // Sprawdzamy, czy obiekt w pobliżu jest MelonGolemEntity
            MelonGolemEntity melonGolem = findNearbyMelonGolem(user);  // Funkcja wyszukiwania golemów w pobliżu (musimy ją stworzyć)

            if (melonGolem != null) {
                // Tworzymy MelonSeedProjectileEntity, przekazując golem jako właściciela
                MelonSeedProjectileEntity melonseed = new MelonSeedProjectileEntity(world, melonGolem);
                melonseed.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 0f); // Prędkość wyrzutu
                world.spawnEntity(melonseed);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));  // Statystyki użycia przedmiotu
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);  // Zużywamy przedmiot
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    // Funkcja do znajdowania pobliskiego MelonGolemEntity
    private MelonGolemEntity findNearbyMelonGolem(PlayerEntity player) {
        // Szukamy w promieniu np. 10 bloków
        double radius = 10.0;
        return player.getWorld().getEntitiesByClass(MelonGolemEntity.class, player.getBoundingBox().expand(radius), null)
                .stream()
                .filter(golem -> golem.isAlive())
                .findFirst()
                .orElse(null);  // Jeśli nie znajdziemy golemu, zwrócimy null
    }

}
