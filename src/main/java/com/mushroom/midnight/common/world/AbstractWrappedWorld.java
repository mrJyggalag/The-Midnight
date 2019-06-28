package com.mushroom.midnight.common.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.ITickList;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class AbstractWrappedWorld implements IWorld {
    private final IWorld world;

    protected AbstractWrappedWorld(IWorld world) {
        this.world = world;
    }

    @Override
    public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
        return this.world.setBlockState(pos, state, flags);
    }

    @Override
    public boolean removeBlock(BlockPos pos, boolean b) {
        return this.world.removeBlock(pos, b);
    }

    @Override
    public boolean destroyBlock(BlockPos pos, boolean b) {
        return this.world.destroyBlock(pos, b);
    }

    @Override
    public boolean hasBlockState(BlockPos pos, Predicate<BlockState> predicate) {
        return this.world.hasBlockState(pos, predicate);
    }

    @Override
    public int getLightSubtracted(BlockPos pos, int amount) {
        return this.world.getLightSubtracted(pos, amount);
    }

    @Nullable
    @Override
    public IChunk getChunk(int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
        return this.world.getChunk(x, z, requiredStatus, nonnull);
    }

    @Override
    public BlockPos getHeight(Heightmap.Type heightmapType, BlockPos pos) {
        return this.world.getHeight(heightmapType, pos);
    }

    @Override
    public int getHeight(Heightmap.Type heightmapType, int x, int z) {
        return this.world.getHeight(heightmapType, x, z);
    }

    @Override
    public int getSkylightSubtracted() {
        return this.world.getSkylightSubtracted();
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.world.getWorldBorder();
    }

    @Override
    public boolean isRemote() {
        return this.world.isRemote();
    }

    @Override
    public int getSeaLevel() {
        return this.world.getSeaLevel();
    }

    @Override
    public Dimension getDimension() {
        return this.world.getDimension();
    }

    @Override
    public long getSeed() {
        return this.world.getSeed();
    }

    @Override
    public ITickList<Block> getPendingBlockTicks() {
        return this.world.getPendingBlockTicks();
    }

    @Override
    public ITickList<Fluid> getPendingFluidTicks() {
        return this.world.getPendingFluidTicks();
    }

    @Override
    public World getWorld() {
        return this.world.getWorld();
    }

    @Override
    public WorldInfo getWorldInfo() {
        return this.world.getWorldInfo();
    }

    @Override
    public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
        return this.world.getDifficultyForLocation(pos);
    }

    @Override
    public AbstractChunkProvider getChunkProvider() {
        return this.world.getChunkProvider();
    }

    @Override
    public Random getRandom() {
        return this.world.getRandom();
    }

    @Override
    public void notifyNeighbors(BlockPos pos, Block block) {
        this.world.notifyNeighbors(pos, block);
    }

    @Override
    public BlockPos getSpawnPoint() {
        return this.world.getSpawnPoint();
    }

    @Override
    public void playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        this.world.playSound(player, pos, sound, category, volume, pitch);
    }

    @Override
    public void addParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        this.world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public void playEvent(@Nullable PlayerEntity player, int type, BlockPos pos, int data) {
        this.world.playEvent(player, type, pos, data);
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return this.world.getTileEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return this.world.getBlockState(pos);
    }

    @Override
    public IFluidState getFluidState(BlockPos pos) {
        return this.world.getFluidState(pos);
    }

    @Override
    public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entity, AxisAlignedBB bounds, @Nullable Predicate<? super Entity> predicate) {
        return this.world.getEntitiesInAABBexcluding(entity, bounds, predicate);
    }

    @Override
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> type, AxisAlignedBB bounds, @Nullable Predicate<? super T> predicate) {
        return this.world.getEntitiesWithinAABB(type, bounds, predicate);
    }

    @Override
    public List<? extends PlayerEntity> getPlayers() {
        return this.world.getPlayers();
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return this.world.getBiome(pos);
    }

    @Override
    public int getLightFor(LightType lightType, BlockPos pos) {
        return this.world.getLightFor(lightType, pos);
    }
}
