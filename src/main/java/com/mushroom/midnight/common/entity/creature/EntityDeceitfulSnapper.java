package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityDeceitfulSnapper extends EntityWaterMob {
    private BlockPos spawnPosition;

    public EntityDeceitfulSnapper(World world) {
        super(world);
        setSize(0.4f, 0.4f);
    }

    @Override
    protected PathNavigateSwimmer createNavigator(World world) {
        return new PathNavigateSwimmer(this, world);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (inWater) {
            if (spawnPosition == null || spawnPosition.distanceSq((double) (int) posX, (double) (int) posY, (double) (int) posZ) < 4d || rand.nextInt(30) == 0) {
                spawnPosition = new BlockPos((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(4) - 1, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
                if (spawnPosition.getY() < 1 || (spawnPosition.getX() == (int) posX && spawnPosition.getZ() == (int) posZ) || world.getBlockState(spawnPosition).getMaterial() != Material.WATER) {
                    spawnPosition = null;
                }
            }
            if (spawnPosition != null) {
                double d0 = (double) spawnPosition.getX() + 0.5d - posX;
                double d1 = (double) spawnPosition.getY() + 0.1d - posY;
                double d2 = (double) spawnPosition.getZ() + 0.5d - posZ;
                motionX += (Math.signum(d0) * 0.2d - motionX) * 0.1d;
                motionY += (Math.signum(d1) * 0.2d - motionY) * 0.1d;
                motionZ += (Math.signum(d2) * 0.2d - motionZ) * 0.1d;
                moveForward = 0.2f;
                rotationYaw += MathHelper.wrapDegrees((float) ((MathHelper.atan2(motionZ, motionX) * (180d / Math.PI)) - 90f) - rotationYaw);
            }
            if (motionY > 0.007f && (Math.abs(motionX) > 0.007f && Math.abs(motionZ) > 0.007f) && world.rand.nextFloat() < 0.001f) {
                this.motionY += 0.8f;
            }
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return ModLootTables.LOOT_TABLE_DECEITFUL_SNAPPER;
    }
}
