package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.Random;

public class MidnightMyceliumBlock extends NightstoneBlock {
    public MidnightMyceliumBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isRemote || !world.isAreaLoaded(pos, 2)) {
            return;
        }

        if (!Helper.isMidnightDimension(world) || world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2) {
            world.setBlockState(pos, MidnightBlocks.NIGHTSTONE.getDefaultState());
        } else {
            if (world.getLightFromNeighbors(pos.up()) >= 2) {
                for (int i = 0; i < 4; ++i) {
                    BlockPos spreadPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    BlockState surfaceState = world.getBlockState(spreadPos);
                    BlockState coverState = world.getBlockState(spreadPos.up());
                    if (surfaceState.getBlock() == MidnightBlocks.NIGHTSTONE && coverState.getLightOpacity(world, spreadPos.up()) <= 2) {
                        world.setBlockState(spreadPos, this.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.MYCELIUM, (double) (pos.getX() + rand.nextFloat()), (double) (pos.getY() + 1.1f), (double) (pos.getZ() + rand.nextFloat()), 0d, 0d, 0d);
        }
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightBlocks.NIGHTSTONE.getItemDropped(MidnightBlocks.NIGHTSTONE.getDefaultState(), rand, fortune);
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader source, BlockPos pos) {
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.SOLID) {
            return source.getCombinedLight(pos, 0);
        }
        return source.getCombinedLight(pos, 10);
    }
}
