package net.romusie.melongolemmod.entity.custom;

import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.romusie.melongolemmod.entity.ModEntities;
import net.romusie.melongolemmod.item.ModItems;

public class MelonSeedProjectileEntity extends PersistentProjectileEntity {
    private float rotation;
    public Vector2f groundedOffset;

    public MelonSeedProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }



    public MelonSeedProjectileEntity(World world, MelonGolemEntity melonGolemEntity) {
        super(ModEntities.MELONSEED, melonGolemEntity, world, new ItemStack(ModItems.MELONSEED), null);

        this.setYaw(melonGolemEntity.getYaw());
        this.setPitch(melonGolemEntity.getPitch());
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.MELONSEED);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1);

        if (!this.getWorld().isClient()) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);
        if (!this.getWorld().isClient()) {
            this.discard(); // Znika jak śnieżka
        }
    }

}
