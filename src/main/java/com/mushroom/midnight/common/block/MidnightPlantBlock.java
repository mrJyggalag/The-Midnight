package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MidnightPlantBlock extends BushBlock implements IShearable, GeneratablePlant {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);
    private final PlantBehaviorType behaviorType;
    private final boolean glowing;

    public MidnightPlantBlock(PlantBehaviorType behaviorType, boolean glowing) {
        super(behaviorType.getMaterial(), MapColor.PURPLE_STAINED_HARDENED_CLAY);
        this.behaviorType = behaviorType;
        this.glowing = glowing;
        if (this.glowing) {
            setLightLevel(0.8F);
        }
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }

    public MidnightPlantBlock(boolean glowing) {
        this(PlantBehaviorType.FLOWER, glowing);
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return Helper.isGroundForMidnightPlant(state.getBlock());
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
        return canSustainBush(world.getBlockState(pos.down()));
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return this.behaviorType.isShearable();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        if (this.behaviorType.isShearable()) {
            return NonNullList.withSize(1, new ItemStack(this));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return this.behaviorType.isReplacable();
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return this.behaviorType.isShearable() ? Items.AIR : super.getItemDropped(state, rand, fortune);
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return glowing ? layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT : super.canRenderInLayer(state, layer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        if (!glowing) {
            return super.getPackedLightmapCoords(state, world, pos);
        }
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            return world.getCombinedLight(pos, 0);
        }
        int skyLight = 14;
        int blockLight = 14;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, Direction face) {
        return 100;
    }
}
