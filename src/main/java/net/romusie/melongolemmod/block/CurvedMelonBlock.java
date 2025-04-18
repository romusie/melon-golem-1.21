package net.romusie.melongolemmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.romusie.melongolemmod.block.ModBlocks;
import net.romusie.melongolemmod.entity.ModEntities;
import net.romusie.melongolemmod.entity.custom.MelonGolemEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.advancement.criterion.Criteria;

public class CurvedMelonBlock extends HorizontalFacingBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    @Nullable
    private BlockPattern melonGolemPattern;

    public CurvedMelonBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!oldState.isOf(state.getBlock())) {
            this.trySpawnMelonGolem(world, pos);
        }
    }

    private void trySpawnMelonGolem(World world, BlockPos pos) {
        BlockPattern.Result result = this.getMelonGolemPattern().searchAround(world, pos);
        if (result != null) {
            MelonGolemEntity golem = ModEntities.MELON_GOLEM.create(world);
            if (golem != null) {
                CurvedMelonBlock.spawnEntity(world, result, golem, result.translate(0, 2, 0).getBlockPos());
            }
        }
    }

    private BlockPattern getMelonGolemPattern() {
        if (this.melonGolemPattern == null) {
            this.melonGolemPattern = BlockPatternBuilder.start()
                    .aisle("^", "#", "#")
                    .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(ModBlocks.CURVED_MELON)))
                    .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
                    .build();
        }
        return this.melonGolemPattern;
    }

    private static void spawnEntity(World world, BlockPattern.Result patternResult, Entity entity, BlockPos pos) {
        for (int i = 0; i < patternResult.getWidth(); i++) {
            for (int j = 0; j < patternResult.getHeight(); j++) {
                CachedBlockPosition cached = patternResult.translate(i, j, 0);
                world.setBlockState(cached.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cached.getBlockPos(), Block.getRawIdFromState(cached.getBlockState()));
            }
        }

        entity.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
        world.spawnEntity(entity);

        for (ServerPlayerEntity player : world.getNonSpectatingEntities(ServerPlayerEntity.class, entity.getBoundingBox().expand(5.0))) {
            Criteria.SUMMONED_ENTITY.trigger(player, entity);
        }
    }
}
