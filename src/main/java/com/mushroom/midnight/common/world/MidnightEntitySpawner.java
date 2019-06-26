package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.EntitySpawnConfigured;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.util.WeightedPool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IMobEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mushroom.midnight.Midnight.MIDNIGHT_MOB;

public final class MidnightEntitySpawner<T extends EntitySpawnConfigured> {
    private static final int MOB_COUNT_DIV = (int) Math.pow(17d, 2d);
    private static final int CHUNK_RANGE = 8;

    private static final long ANIMAL_SPAWN_INTERVAL = 400;

    private final Function<BlockPos, T> biomeFunction;
    private final SurfacePlacementLevel placementLevel;

    private final Set<ChunkPos> eligibleSpawnChunks = new HashSet<>();

    public MidnightEntitySpawner(Function<BlockPos, T> biomeFunction, SurfacePlacementLevel placementLevel) {
        this.biomeFunction = biomeFunction;
        this.placementLevel = placementLevel;
    }

    public void spawnAroundPlayers(ServerWorld world) {
        Set<ChunkPos> spawnChunks = this.computeEligibleSpawnChunks(world);

        Collection<EnumCreatureType> validCreatureTypes = Arrays.stream(EnumCreatureType.values())
                .filter(c -> this.shouldSpawnCreatureType(world, c))
                .collect(Collectors.toList());

        if (validCreatureTypes.isEmpty()) {
            return;
        }

        CreatureTypeCount entityCount = CreatureTypeCount.count(world, validCreatureTypes);
        for (EnumCreatureType creatureType : validCreatureTypes) {
            int actualCreatureCount = entityCount.getCount(creatureType);
            int maxCreatureCount = creatureType.getMaxNumberOfCreature() * spawnChunks.size() / MOB_COUNT_DIV;
            if (actualCreatureCount <= maxCreatureCount) {
                this.spawnCreaturesOfType(world, spawnChunks, creatureType);
            }
        }
    }

    public void populateChunk(World world, int chunkX, int chunkZ, Random random) {
        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        int originX = globalX + 8;
        int originZ = globalZ + 8;

        BlockPos centerPos = new BlockPos(globalX + 16, 0, globalZ + 16);
        T biome = this.biomeFunction.apply(centerPos);

        SpawnerConfig spawnerConfig = biome.getSpawnerConfig();
        if (spawnerConfig.isEmpty()) {
            return;
        }

        while (random.nextFloat() < spawnerConfig.getSpawnChance()) {
            WeightedPool<Biome.SpawnListEntry> pool = spawnerConfig.getPool(EnumCreatureType.CREATURE);
            Biome.SpawnListEntry entry = pool.pick(world.rand);
            if (entry == null) {
                continue;
            }

            this.spawnGroupInChunk(world, originX, originZ, random, entry);
        }
    }

    private void spawnCreaturesOfType(ServerWorld world, Set<ChunkPos> spawnChunks, EnumCreatureType creatureType) {
        List<ChunkPos> shuffledChunks = new ArrayList<>(spawnChunks);
        Collections.shuffle(shuffledChunks);

        for (ChunkPos chunkPos : shuffledChunks) {
            BlockPos pos = this.getRandomPositionInChunk(world, chunkPos.x, chunkPos.z);
            BlockState state = world.getBlockState(pos);
            if (state.isNormalCube()) {
                continue;
            }

            this.spawnEntitiesAround(world, pos, creatureType);
            if (creatureType == MIDNIGHT_MOB) { break; } // spawn slowly monsters
        }
    }

    private void spawnEntitiesAround(ServerWorld world, BlockPos pos, EnumCreatureType creatureType) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int spawnedEntities = 0;

        for (int i = 0; i < 3; i++) {
            int range = 6;
            Biome.SpawnListEntry selectedEntry = null;
            IMobEntityData livingData = null;

            mutablePos.setPos(pos);

            int spawnGroupSize = MathHelper.ceil(Math.random() * 4.0);
            for (int groupIndex = 0; groupIndex < spawnGroupSize; groupIndex++) {
                mutablePos.setPos(
                        mutablePos.getX() + world.rand.nextInt(range) - world.rand.nextInt(range),
                        mutablePos.getY() + world.rand.nextInt(1) - world.rand.nextInt(1),
                        mutablePos.getZ() + world.rand.nextInt(range) - world.rand.nextInt(range)
                );

                if (world.isPlayerWithin(mutablePos.getX() + 0.5, mutablePos.getY(), mutablePos.getZ() + 0.5, 24)) {
                    continue;
                }

                if (selectedEntry == null) {
                    T biome = this.biomeFunction.apply(pos);
                    WeightedPool<Biome.SpawnListEntry> pool = biome.getSpawnerConfig().getPool(creatureType);

                    selectedEntry = pool.pick(world.rand);
                    if (selectedEntry == null) {
                        break;
                    }
                }

                if (this.canSpawnAt(world, creatureType, mutablePos, selectedEntry)) {
                    MobEntity creature = this.createEntity(world, mutablePos, selectedEntry);

                    float spawnX = mutablePos.getX() + 0.5f;
                    float spawnY = mutablePos.getY();
                    float spawnZ = mutablePos.getZ() + 0.5f;

                    Event.Result spawnResult = ForgeEventFactory.canEntitySpawn(creature, world, spawnX, spawnY, spawnZ, null);
                    if (spawnResult != Event.Result.ALLOW && spawnResult != Event.Result.DEFAULT) {
                        continue;
                    }

                    if (creature.getCanSpawnHere() && creature.isNotColliding()) {
                        if (!ForgeEventFactory.doSpecialSpawn(creature, world, spawnX, spawnY, spawnZ, null)) {
                            livingData = creature.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(creature)), livingData);
                        }

                        if (creature.isNotColliding()) {
                            spawnedEntities++;
                            world.addEntity(creature);
                        } else {
                            creature.remove();
                        }

                        if (spawnedEntities >= ForgeEventFactory.getMaxSpawnPackSize(creature)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void spawnGroupInChunk(World world, int originX, int originZ, Random random, Biome.SpawnListEntry spawnEntry) {
        int groupSize = spawnEntry.minGroupCount + random.nextInt(spawnEntry.maxGroupCount - spawnEntry.minGroupCount + 1);
        int centerX = originX + random.nextInt(16);
        int centerZ = originZ + random.nextInt(16);

        int x = centerX;
        int z = centerZ;

        IMobEntityData livingData = null;

        for (int i = 0; i < groupSize; i++) {
            for (int attempt = 0; attempt < 4; attempt++) {
                boolean spawnedEntity = false;

                BlockPos pos = this.placementLevel.getSurfacePos(world, new BlockPos(x, 0, z));
                if (this.isSpawnPositionValid(MobEntity.SpawnPlacementType.ON_GROUND, world, pos)) {
                    MobEntity creature = this.createEntity(world, pos, spawnEntry);
                    if (ForgeEventFactory.canEntitySpawn(creature, world, x + 0.5F, pos.getY(), z + 0.5F, null) == Event.Result.DENY) {
                        creature.setDead();
                        continue;
                    }

                    world.spawnEntity(creature);
                    livingData = creature.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(creature)), livingData);
                    spawnedEntity = true;
                }

                x += random.nextInt(5) - random.nextInt(5);
                z += random.nextInt(5) - random.nextInt(5);

                while (x < originX || x >= originX + 16 || z < originZ || z >= originZ + 16) {
                    x = centerX + random.nextInt(5) - random.nextInt(5);
                    z = centerZ + random.nextInt(5) - random.nextInt(5);
                }

                if (spawnedEntity) {
                    break;
                }
            }
        }
    }

    private MobEntity createEntity(World world, BlockPos pos, Biome.SpawnListEntry selectedEntry) {
        try {
            MobEntity creature = selectedEntry.newInstance(world);
            float yaw = world.rand.nextFloat() * 360.0F;
            creature.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, yaw, 0.0F);
            return creature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to spawn entry " + selectedEntry, e);
        }
    }

    private boolean canSpawnAt(ServerWorld world, EnumCreatureType creatureType, BlockPos pos, Biome.SpawnListEntry selectedEntry) {
        MobEntity.SpawnPlacementType placementType = EntitySpawnPlacementRegistry.getPlacementForEntity(selectedEntry.entityClass);
        return this.isSpawnPositionValid(placementType, world, pos);
    }

    private Set<ChunkPos> computeEligibleSpawnChunks(ServerWorld world) {
        this.eligibleSpawnChunks.clear();

        Stream<PlayerEntity> playerStream = this.getPlayerStream(world);
        playerStream.forEach(player -> {
            int playerChunkX = MathHelper.floor(player.posX) >> 4;
            int playerChunkZ = MathHelper.floor(player.posZ) >> 4;
            for (int z = -CHUNK_RANGE; z <= CHUNK_RANGE; z++) {
                for (int x = -CHUNK_RANGE; x <= CHUNK_RANGE; x++) {
                    boolean isLimit = x == -CHUNK_RANGE || x == CHUNK_RANGE || z == -CHUNK_RANGE || z == CHUNK_RANGE;
                    ChunkPos chunkPos = new ChunkPos(playerChunkX + x, playerChunkZ + z);
                    if (!isLimit && world.getWorldBorder().contains(chunkPos)) {
                        PlayerChunkMapEntry trackedChunk = world.getPlayerChunkMap().getEntry(chunkPos.x, chunkPos.z);
                        if (trackedChunk != null && trackedChunk.isSentToPlayers()) {
                            this.eligibleSpawnChunks.add(chunkPos);
                        }
                    }
                }
            }
        });

        return this.eligibleSpawnChunks;
    }

    private Stream<? extends PlayerEntity> getPlayerStream(World world) {
        return world.getPlayers().stream().filter(player -> !player.isSpectator());
    }

    private boolean shouldSpawnCreatureType(World world, EnumCreatureType creatureType) {
        if (!creatureType.getPeacefulCreature() && world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (creatureType == MIDNIGHT_MOB) {
            return world.getTotalWorldTime() % MidnightConfig.general.monsterSpawnRate == 0;
        }
        return !creatureType.getAnimal() || world.getTotalWorldTime() % ANIMAL_SPAWN_INTERVAL == 0;
    }

    private BlockPos getRandomPositionInChunk(World world, int chunkX, int chunkZ) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        int x = (chunkX << 4) + world.rand.nextInt(16);
        int z = (chunkZ << 4) + world.rand.nextInt(16);

        int surfaceY = MathHelper.roundUp(this.placementLevel.getSurfacePos(world, new BlockPos(x, 0, z)).getY() + 1, 16);
        int y = world.rand.nextInt(surfaceY > 0 ? surfaceY : chunk.getTopFilledSegment() + 16 - 1);

        return new BlockPos(x, y, z);
    }

    private boolean isSpawnPositionValid(MobEntity.SpawnPlacementType placementType, World world, BlockPos pos) {
        return world.getWorldBorder().contains(pos) && placementType.canSpawnAt(world, pos);
    }
}
