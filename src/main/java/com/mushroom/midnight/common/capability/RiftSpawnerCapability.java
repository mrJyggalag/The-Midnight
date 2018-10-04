package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entities.EntityRift;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Vector3d;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public interface RiftSpawnerCapability extends ICapabilitySerializable<NBTTagCompound> {
    void update(World world);

    class Impl implements RiftSpawnerCapability {
        private static final double RIFT_SEPARATION = 64.0;
        private static final double PLAYER_SEPARATION = 16.0;

        private static final int REGION_RANGE = 64 >> 5;

        @Override
        public void update(World world) {
            if (!world.isDaytime()) {
                Random random = world.rand;
                List<EntityPlayer> players = world.playerEntities;

                Set<BlockPos> spawnRegions = new HashSet<>();

                for (EntityPlayer player : players) {
                    int regionX = (int) player.posX >> 5;
                    int regionY = (int) player.posY >> 5;
                    int regionZ = (int) player.posZ >> 5;
                    for (BlockPos pos : BlockPos.getAllInBox(
                            regionX - REGION_RANGE, regionY - REGION_RANGE, regionZ - REGION_RANGE,
                            regionX + REGION_RANGE, regionY + REGION_RANGE, regionZ + REGION_RANGE
                    )) {
                        spawnRegions.add(pos);
                    }
                }

                for (BlockPos spawnRegion : spawnRegions) {
                    BlockPos riftPosition = this.generateRiftPosition(random, spawnRegion);
                    if (world.isBlockLoaded(riftPosition) && world.isAirBlock(riftPosition)) {
                        Vector3d correctedPosition = this.getCorrectedRiftPosition(world, riftPosition);
                        if (correctedPosition == null || !this.canRiftSpawn(world, correctedPosition)) {
                            continue;
                        }
                        this.spawnRift(world, correctedPosition);
                    }
                }
            }
        }

        private void spawnRift(World world, Vector3d position) {
            EntityRift rift = new EntityRift(world);

            float yaw = world.rand.nextFloat() * 360.0F;
            rift.setPositionAndRotation(position.x, position.y, position.z, yaw, 0.0F);

            System.out.println("Spawn rift at " + position);

            world.spawnEntity(rift);
        }

        private boolean canRiftSpawn(World world, Vector3d pos) {
            AxisAlignedBB playerBounds = new AxisAlignedBB(
                    pos.x - PLAYER_SEPARATION, pos.y - PLAYER_SEPARATION, pos.z - PLAYER_SEPARATION,
                    pos.x + PLAYER_SEPARATION, pos.y + PLAYER_SEPARATION, pos.z + PLAYER_SEPARATION
            );
            if (!world.getEntitiesWithinAABB(EntityPlayer.class, playerBounds).isEmpty()) {
                return false;
            }

            AxisAlignedBB riftBounds = new AxisAlignedBB(
                    pos.x - RIFT_SEPARATION, pos.y - RIFT_SEPARATION, pos.z - RIFT_SEPARATION,
                    pos.x + RIFT_SEPARATION, pos.y + RIFT_SEPARATION, pos.z + RIFT_SEPARATION
            );

            if (!world.getEntitiesWithinAABB(EntityRift.class, riftBounds).isEmpty()) {
                return false;
            }

            AxisAlignedBB bounds = new AxisAlignedBB(
                    pos.x - 1.0, pos.y, pos.z - 1.0,
                    pos.x + 1.0, pos.y + 2.0, pos.z + 1.0
            );
            return world.getCollisionBoxes(null, bounds).isEmpty();
        }

        private BlockPos generateRiftPosition(Random random, BlockPos region) {
            int x = (region.getX() << 5) + random.nextInt(32);
            int y = (region.getY() << 5) + random.nextInt(32);
            int z = (region.getZ() << 5) + random.nextInt(32);
            return new BlockPos(x, y, z);
        }

        @Nullable
        private Vector3d getCorrectedRiftPosition(World world, BlockPos pos) {
            BlockPos surface = this.findSurface(world, pos, 6);
            if (surface != null) {
                BlockPos spawnPosition = surface.up();
                return new Vector3d(spawnPosition.getX() + 0.5, spawnPosition.getY() + 0.5, spawnPosition.getZ() + 0.5);
            }
            return null;
        }

        @Nullable
        private BlockPos findSurface(World world, BlockPos pos, int maxSteps) {
            int steps = 0;
            while (!world.isSideSolid(pos, EnumFacing.UP)) {
                pos = pos.down();
                if (steps++ > maxSteps) {
                    return null;
                }
            }
            return pos;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == Midnight.riftSpawnerCap;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == Midnight.riftSpawnerCap) {
                return Midnight.riftSpawnerCap.cast(this);
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound) {

        }
    }
}
