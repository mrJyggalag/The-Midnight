package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMidnightMycelium extends BlockNightstone {

    public BlockMidnightMycelium() {
        super();
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote || !world.isAreaLoaded(pos, 2)) {
            return;
        }
        if (!Helper.isMidnightDimension(world) || world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2) {
            world.setBlockState(pos, ModBlocks.NIGHTSTONE.getDefaultState());
        } else {
            if (world.getLightFromNeighbors(pos.up()) >= 2) {
                for (int i = 0; i < 4; ++i) {
                    BlockPos spreadPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    IBlockState surfaceState = world.getBlockState(spreadPos);
                    IBlockState coverState = world.getBlockState(spreadPos.up());
                    if (surfaceState.getBlock() == ModBlocks.NIGHTSTONE && coverState.getLightOpacity(world, spreadPos.up()) <= 2) {
                        world.setBlockState(spreadPos, this.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(10) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (double) (pos.getX() + rand.nextFloat()), (double) (pos.getY() + 1.1f), (double) (pos.getZ() + rand.nextFloat()), 0d, 0d, 0d);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModBlocks.NIGHTSTONE.getItemDropped(ModBlocks.NIGHTSTONE.getDefaultState(), rand, fortune);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.SOLID) {
            return source.getCombinedLight(pos, 0);
        }
        return source.getCombinedLight(pos, 10);
    }
}
