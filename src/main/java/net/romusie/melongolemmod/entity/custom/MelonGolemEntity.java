package net.romusie.melongolemmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MelonGolemEntity extends GolemEntity implements RangedAttackMob {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }


    public MelonGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMelonGolemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MelonGolemAttackGoal(this, 1.25, 20, 10.0F)); // Używamy naszej serii
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0, 1.0000001E-5F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, MobEntity.class, 10, true, false, entity -> entity instanceof Monster));
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            if (this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.SNOW_GOLEM_MELTS)) {
                this.damage(this.getDamageSources().onFire(), 1.0F);
            }

            if (!this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return;
            }

            BlockState blockState = Blocks.SNOW.getDefaultState();

            for (int i = 0; i < 4; i++) {
                int j = MathHelper.floor(this.getX() + (i % 2 * 2 - 1) * 0.25F);
                int k = MathHelper.floor(this.getY());
                int l = MathHelper.floor(this.getZ() + (i / 2 % 2 * 2 - 1) * 0.25F);
                BlockPos blockPos = new BlockPos(j, k, l);
                if (this.getWorld().getBlockState(blockPos).isAir() && blockState.canPlaceAt(this.getWorld(), blockPos)) {
                    this.getWorld().setBlockState(blockPos, blockState);
                    this.getWorld().emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(this, blockState));
                }
            }
        }
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        MelonSeedProjectileEntity melonSeedProjectile = new MelonSeedProjectileEntity(this.getWorld(), this); // Tworzymy nasz niestandardowy rzucony obiekt
        double d = target.getEyeY() - 1.1F; // Wysokość celu
        double e = target.getX() - this.getX(); // Współrzędne celu
        double f = d - melonSeedProjectile.getY(); // Współrzędne Y
        double g = target.getZ() - this.getZ(); // Współrzędne Z
        double h = Math.sqrt(e * e + g * g) * 0.2F; // Wyliczamy wektor trajektorii

        melonSeedProjectile.setVelocity(e, f + h, g, 1.6F, 12.0F); // Ustawiamy prędkość oraz kąt

        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F)); // Graj dźwięk
        this.getWorld().spawnEntity(melonSeedProjectile); // Tworzymy nasz rzucony obiekt w świecie
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.75F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
    }

    // Nasza niestandardowa klasa celu strzelania seriami
    private static class MelonGolemAttackGoal extends Goal {
        private final RangedAttackMob attacker;
        private final double speed;
        private final int attackIntervalTicks;
        private final float maxShootRange;

        private int attackTime = -1;
        private int burstCount = 0;

        public MelonGolemAttackGoal(RangedAttackMob attacker, double speed, int attackIntervalTicks, float maxShootRange) {
            this.attacker = attacker;
            this.speed = speed;
            this.attackIntervalTicks = attackIntervalTicks;
            this.maxShootRange = maxShootRange * maxShootRange;
        }

        @Override
        public boolean canStart() {
            return getTarget() != null && getTarget().isAlive();
        }

        @Override
        public void start() {
            this.attackTime = 0;
            this.burstCount = 0;
        }

        @Override
        public void stop() {
            this.attackTime = -1;
            this.burstCount = 0;
        }

        @Override
        public void tick() {
            LivingEntity target = getTarget();
            if (target == null) return;

            double distanceSq = ((MobEntity) attacker).squaredDistanceTo(target);
            boolean canSee = ((MobEntity) attacker).getVisibilityCache().canSee(target);

            if (canSee && distanceSq <= this.maxShootRange) {
                ((MobEntity) attacker).getLookControl().lookAt(target, 30.0F, 30.0F);

                if (attackTime <= 0) {
                    attacker.shootAt(target, 1.0F);
                    burstCount++;
                    attackTime = 2; // odstęp między strzałami

                    if (burstCount >= 4) {
                        attackTime = attackIntervalTicks; // przerwa po serii
                        burstCount = 0;
                    }
                } else {
                    attackTime--;
                }
            }
        }

        private LivingEntity getTarget() {
            return ((MobEntity) attacker).getTarget();
        }
    }
}
