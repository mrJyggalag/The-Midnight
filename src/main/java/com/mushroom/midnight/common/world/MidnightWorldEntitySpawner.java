package com.mushroom.midnight.common.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("deprecation")
public final class MidnightWorldEntitySpawner {
    private static final int MOB_COUNT_DIV = (int) Math.pow(17d, 2d);
    private static final Set<ChunkPos> eligibleChunksForSpawning = Sets.newHashSet();

    public static int findChunksForSpawning(WorldServer world) {
        boolean spawnHostileMobs = world.getDifficulty() != EnumDifficulty.PEACEFUL;
        boolean spawnPeacefulMobs = true;
        boolean spawnOnSetTickRate = world.getWorldInfo().getWorldTotalTime() % 400L == 0L;

        if (!spawnHostileMobs && !spawnPeacefulMobs) {
            return 0;
        } else {
            /* list the eligible chunks around players where mobs can spawn */
            eligibleChunksForSpawning.clear();
            int chunkCount = 0;
            for (EntityPlayer player : world.playerEntities) {
                if (!player.isSpectator()) {
                    int chunkPosX = MathHelper.floor(player.posX) >> 4;
                    int chunkPosZ = MathHelper.floor(player.posZ) >> 4;
                    int range = 8; // area 17X17
                    for (int x = -range; x <= range; ++x) {
                        for (int z = -range; z <= range; ++z) {
                            boolean isLimit = x == -range || x == range || z == -range || z == range;
                            ChunkPos chunkpos = new ChunkPos(chunkPosX + x, chunkPosZ + z);
                            if (!eligibleChunksForSpawning.contains(chunkpos)) {
                                ++chunkCount;
                                if (!isLimit && world.getWorldBorder().contains(chunkpos)) {
                                    PlayerChunkMapEntry mapEntry = world.getPlayerChunkMap().getEntry(chunkpos.x, chunkpos.z);
                                    if (mapEntry != null && mapEntry.isSentToPlayers()) {
                                        eligibleChunksForSpawning.add(chunkpos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /* iterate each creatureType to spawn */
            int entitySpawnCount = 0;
            for (EnumCreatureType creatureType : EnumCreatureType.values()) {
                // creatureType with the flag isAnimal to false spawn every tick (when true, it spawns every 400 ticks)
                if ((!creatureType.getPeacefulCreature() || spawnPeacefulMobs) && (creatureType.getPeacefulCreature() || spawnHostileMobs) && (!creatureType.getAnimal() || spawnOnSetTickRate)) {
                    int actualCreatureCount = world.countEntities(creatureType, true);
                    int maxCreatureCount = creatureType.getMaxNumberOfCreature() * chunkCount / MOB_COUNT_DIV;
                    if (actualCreatureCount <= maxCreatureCount) {
                        ArrayList<ChunkPos> shuffled = Lists.newArrayList(eligibleChunksForSpawning);
                        Collections.shuffle(shuffled);
                        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
                        label134:
                        for (ChunkPos chunkPos : shuffled) {
                            BlockPos pos = getRandomChunkPosition(world, chunkPos.x, chunkPos.z);
                            IBlockState state = world.getBlockState(pos);
                            if (!state.isNormalCube()) {
                                int entitySpawnInChunk = 0;
                                for (int i = 0; i < 3; ++i) {
                                    int posX = pos.getX();
                                    int posY = pos.getY();
                                    int posZ = pos.getZ();
                                    int range = 6;
                                    Biome.SpawnListEntry spawnEntry = null;
                                    int maxSpawnEntry = MathHelper.ceil(Math.random() * 4d);
                                    for (int j = 0; j < maxSpawnEntry; ++j) {
                                        posX += world.rand.nextInt(range) - world.rand.nextInt(range);
                                        posY += world.rand.nextInt(1) - world.rand.nextInt(1);
                                        posZ += world.rand.nextInt(range) - world.rand.nextInt(range);
                                        mutablePos.setPos(posX, posY, posZ);
                                        if (!world.isAnyPlayerWithinRangeAt(posX + 0.5d, (double) posY, posZ + 0.5d, 24d)) { // && world.getSpawnPoint().distanceSq(posX + 0.5d, (double) posY, posZ + 0.5d) >= 576d) {
                                            if (spawnEntry == null) {
                                                //spawnEntry = world.getSpawnListEntryForTypeAt(creatureType, mutablePos);
                                                // TODO change MidnightChunkGenerator.getPossibleCreatures() to return the underground biome spawn entries
                                                List<Biome.SpawnListEntry> list = world.getChunkProvider().chunkGenerator.getPossibleCreatures(creatureType, mutablePos);
                                                list = ForgeEventFactory.getPotentialSpawns(world, creatureType, pos, list);
                                                spawnEntry = list != null && !list.isEmpty() ? WeightedRandom.getRandomItem(world.rand, list) : null;
                                                if (spawnEntry == null) {
                                                    break;
                                                }
                                            }
                                            if (world.canCreatureTypeSpawnHere(creatureType, spawnEntry, mutablePos) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(spawnEntry.entityClass), world, mutablePos)) {
                                                EntityLiving creature;
                                                try {
                                                    creature = spawnEntry.newInstance(world);
                                                } catch (Exception exception) {
                                                    exception.printStackTrace();
                                                    return entitySpawnCount;
                                                }
                                                creature.setLocationAndAngles(posX + 0.5d, (double) posY, posZ + 0.5d, world.rand.nextFloat() * 360f, 0f);
                                                Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(creature, world, posX + 0.5f, posY, posZ + 0.5f, false);
                                                if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && (creature.getCanSpawnHere() && creature.isNotColliding()))) {
                                                    if (!ForgeEventFactory.doSpecialSpawn(creature, world, posX + 0.5f, posY, posZ + 0.5f)) {
                                                        creature.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(creature)), null);
                                                    }
                                                    if (creature.isNotColliding()) {
                                                        ++entitySpawnInChunk;
                                                        world.spawnEntity(creature);
                                                    } else {
                                                        creature.setDead();
                                                    }
                                                    if (entitySpawnInChunk >= ForgeEventFactory.getMaxSpawnPackSize(creature)) {
                                                        continue label134;
                                                    }
                                                }
                                                entitySpawnCount += entitySpawnInChunk;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return entitySpawnCount;
        }
    }

    private static BlockPos getRandomChunkPosition(World world, int x, int z) {
        Chunk chunk = world.getChunk(x, z);
        int i = x * 16 + world.rand.nextInt(16);
        int j = z * 16 + world.rand.nextInt(16);
        int k = MathHelper.roundUp(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
        int l = world.rand.nextInt(k > 0 ? k : chunk.getTopFilledSegment() + 16 - 1);
        return new BlockPos(i, l, j);
    }

    private static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType placementType, World world, BlockPos pos) {
        return world.getWorldBorder().contains(pos) && placementType.canSpawnAt(world, pos);
    }

    public static void performWorldGenSpawning(World world, Biome biome, int centerX, int centerZ, int diameterX, int diameterZ, Random random) {
        List<Biome.SpawnListEntry> list = biome.getSpawnableList(EnumCreatureType.CREATURE);
        if (!list.isEmpty()) {
            while (random.nextFloat() < biome.getSpawningChance()) {
                Biome.SpawnListEntry spawnEntry = WeightedRandom.getRandomItem(world.rand, list);
                int i = spawnEntry.minGroupCount + random.nextInt(1 + spawnEntry.maxGroupCount - spawnEntry.minGroupCount);
                IEntityLivingData ientitylivingdata = null;
                int j = centerX + random.nextInt(diameterX);
                int k = centerZ + random.nextInt(diameterZ);
                int l = j;
                int i1 = k;
                for (int j1 = 0; j1 < i; ++j1) {
                    boolean flag = false;
                    for (int k1 = 0; !flag && k1 < 4; ++k1) {
                        BlockPos blockpos = world.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
                        if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, world, blockpos)) {
                            EntityLiving creature;
                            try {
                                creature = spawnEntry.newInstance(world);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                continue;
                            }
                            if (ForgeEventFactory.canEntitySpawn(creature, world, j + 0.5f, (float) blockpos.getY(), k + 0.5f, false) == Event.Result.DENY) {
                                continue;
                            }
                            creature.setLocationAndAngles((double) ((float) j + 0.5f), (double) blockpos.getY(), (double) ((float) k + 0.5f), random.nextFloat() * 360f, 0f);
                            world.spawnEntity(creature);
                            creature.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(creature)), ientitylivingdata);
                            flag = true;
                        }
                        j += random.nextInt(5) - random.nextInt(5);
                        for (k += random.nextInt(5) - random.nextInt(5); j < centerX || j >= centerX + diameterX || k < centerZ || k >= centerZ + diameterX; k = i1 + random.nextInt(5) - random.nextInt(5)) {
                            j = l + random.nextInt(5) - random.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
