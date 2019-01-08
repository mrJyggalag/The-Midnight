package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RiftSpawnHandler {
    private static final double RIFT_SEPARATION = 64.0;
    private static final double PLAYER_SEPARATION = 16.0;

    private static final int REGION_RANGE = 64 >> 5;

    private static boolean warnedOverworldUnloaded;

    public static void update() {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server == null) {
            return;
        }

        World overworld = DimensionManager.getWorld(0);
        if (overworld == null) {
            if (!warnedOverworldUnloaded) {
                Midnight.LOGGER.warn("Overworld unloaded! Cannot spawn Midnight rifts");
                warnedOverworldUnloaded = true;
            }
            return;
        }

        if (!overworld.getGameRules().getBoolean("doRiftSpawning")) {
            return;
        }

        World midnight = DimensionManager.getWorld(ModDimensions.MIDNIGHT.getId());
        if (!overworld.isDaytime()) {
            Random random = overworld.rand;
            Set<BlockPos> spawnRegions = collectPlayerRegions(overworld.playerEntities);

            if (midnight != null) {
                spawnRegions.addAll(collectPlayerRegions(midnight.playerEntities));
            }

            for (BlockPos spawnRegion : spawnRegions) {
                if (random.nextInt(MidnightConfig.general.riftSpawnRarity) == 0) {
                    BlockPos riftPosition = generateRiftPosition(random, spawnRegion);
                    if (midnight != null && (overworld.getWorldBorder().contains(riftPosition) != midnight.getWorldBorder().contains(riftPosition))) {
                        continue;
                    }
                    if (overworld.isBlockLoaded(riftPosition)) {
                        trySpawnRift(overworld, riftPosition, overworld);
                    } else if (midnight != null && midnight.isBlockLoaded(riftPosition)) {
                        trySpawnRift(midnight, riftPosition, overworld);
                    }
                }
            }
        }
    }

    private static Set<BlockPos> collectPlayerRegions(List<EntityPlayer> players) {
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
        return spawnRegions;
    }

    private static void trySpawnRift(World world, BlockPos riftPosition, World overworld) {
        if (world.isAirBlock(riftPosition)) {
            BlockPos correctedPosition = WorldUtil.findSurface(world, riftPosition, 6);
            if (correctedPosition == null || !canRiftSpawn(world, correctedPosition, overworld)) {
                return;
            }
            spawnRift(world, correctedPosition);
        }
    }

    private static void spawnRift(World world, BlockPos pos) {
        float yaw = world.rand.nextFloat() * 360.0F;
        RiftAttachment attachment = new RiftAttachment(pos, yaw);

        BridgeManager trackerHandler = GlobalBridgeManager.getServer();
        RiftBridge bridge = trackerHandler.createBridge(attachment);

        EntityRift rift = new EntityRift(world);
        rift.acceptBridge(bridge);
        bridge.getAttachment().apply(rift);

        trackerHandler.addBridge(bridge);

        world.spawnEntity(rift);
    }

    private static boolean canRiftSpawn(World world, BlockPos pos, World overworld) {
        if (world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, PLAYER_SEPARATION)) {
            return false;
        }
        AxisAlignedBB riftBounds = new AxisAlignedBB(pos).grow(RIFT_SEPARATION);
        if (!world.getEntitiesWithinAABB(EntityRift.class, riftBounds).isEmpty()) {
            return false;
        }
        AxisAlignedBB bounds = new AxisAlignedBB(pos).grow(1.0, 0.0, 1.0).expand(0.0, 1.0, 0.0);
        if (!world.getCollisionBoxes(null, bounds).isEmpty()) {
            return false;
        }
        return !overworld.containsAnyLiquid(bounds) && (Helper.isMidnightDimension(world) || world.getLightFor(EnumSkyBlock.BLOCK, pos) < 4);
    }

    private static BlockPos generateRiftPosition(Random random, BlockPos region) {
        int x = (region.getX() << 5) + random.nextInt(32);
        int y = (region.getY() << 5) + random.nextInt(32);
        int z = (region.getZ() << 5) + random.nextInt(32);
        return new BlockPos(x, y, z);
    }
}
