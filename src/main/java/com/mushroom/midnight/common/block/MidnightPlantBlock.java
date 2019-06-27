package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;

// TODO: Shearability in loot table
// TODO: subclass overriding isReplaceable
public class MidnightPlantBlock extends BushBlock implements GeneratablePlant {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    private boolean glowing;
    private boolean replacable;

    public MidnightPlantBlock(Block.Properties properties) {
        super(properties);
        if (this.glowing) {
            setLightLevel(0.8F);
        }
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }

    public MidnightPlantBlock setReplacable() {
        this.replacable = true;
        return this;
    }

    public MidnightPlantBlock setGlowing() {
        this.glowing = true;
        return this;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Helper.isGroundForMidnightPlant(state.getBlock());
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return this.replacable;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        if (this.glowing) {
            return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
        }
        return super.canRenderInLayer(state, layer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        if (!this.glowing) {
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
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }
}
