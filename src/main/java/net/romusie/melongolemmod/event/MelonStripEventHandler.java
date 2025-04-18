package net.romusie.melongolemmod.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.romusie.melongolemmod.block.CurvedMelonBlock;
import net.romusie.melongolemmod.block.ModBlocks;

public class MelonStripEventHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient && hand == Hand.MAIN_HAND) {
                if (world.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.MELON) {
                    if (player.getStackInHand(hand).getItem() == Items.SHEARS) {
                        world.setBlockState(hitResult.getBlockPos(), ModBlocks.CURVED_MELON.getDefaultState()
                                .with(CurvedMelonBlock.FACING, player.getHorizontalFacing().getOpposite()));  // Ustawiamy kierunek na przeciwny do gracza
                        world.playSound(null, hitResult.getBlockPos(), SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });

    }
}
