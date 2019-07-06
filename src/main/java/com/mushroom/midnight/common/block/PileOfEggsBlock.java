package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings({ "WeakerAccess", "deprecation" })
public abstract class PileOfEggsBlock extends Block {
    public static final SoundType PILE_OF_EGGS = new SoundType(1.0F, 1.0F, MidnightSounds.EGG_CRACKED, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    protected static final VoxelShape bound_one_egg = makeCuboidShape(3.0, 0.0, 3.0, 13.0, 7.0, 13.0);
    protected static final VoxelShape bound_several_eggs = makeCuboidShape(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
    public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 4);

    protected PileOfEggsBlock() {
        super(Properties.create(Material.ROCK).sound(PILE_OF_EGGS).hardnessAndResistance(-1f, 0f));
        setDefaultState(getStateContainer().getBaseState().with(EGGS, 1));
    }

    protected abstract MobEntity createEntityForEgg(World world, BlockPos pos, BlockState state);

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player == null) {
            return false;
        }
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == Item.getItemFromBlock(this)) {
            if (state.get(EGGS) < 4 && !player.getCooldownTracker().hasCooldown(stack.getItem())) {
                player.getCooldownTracker().setCooldown(stack.getItem(), 10);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    world.setBlockState(pos, state.with(EGGS, state.get(EGGS) + 1));
                    world.playSound(null, pos, this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1f) / 2f, this.soundType.getPitch() * 0.8f);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (canTrample(entity)) {
            onTrample(world, pos, entity, 0.05f);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (canTrample(entity)) {
            onTrample(world, pos, entity, 1f);
        }
    }

    protected boolean canTrample(Entity entity) {
        return !(entity instanceof StingerEntity);
    }

    protected void onTrample(World world, BlockPos pos, Entity entity, float chance) {
        if (!world.isRemote && canTrample(entity) && (chance >= 1f || world.rand.nextFloat() <= chance)) {
            breakEggs(world, pos, world.getBlockState(pos));
        }
    }

    protected void breakEggs(World world, BlockPos pos, BlockState state) {
        if (world.isRemote) { return; }
        world.playSound(null, pos, PILE_OF_EGGS.getBreakSound(), SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
        int eggs = state.get(EGGS);
        if (eggs <= 1) {
            world.destroyBlock(pos, false);
        } else {
            world.setBlockState(pos, state.with(EGGS, --eggs), 2);
            world.playEvent(2001, pos, getStateId(state));
        }
        // TODO check this
        /*
        if (harvesters.get() != null) {
            ItemStack stack = harvesters.get().getHeldItemMainhand();
            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            ArrayList<ItemStack> drops = Lists.newArrayList(new ItemStack(this));
            float chance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 ? 0.6f : 0.2f + (0.1f * fortune)), false, harvesters.get());
            if (world.rand.nextFloat() <= chance) {
                drops.forEach(c -> spawnAsEntity(world, pos, c));
                return;
            }
        }*/

        MobEntity creature;
        try {
            creature = createEntityForEgg(world, pos, state);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        creature.setPositionAndRotation(pos.getX() + world.rand.nextFloat(), pos.getY() + 0.45f, pos.getZ() + world.rand.nextFloat(), world.rand.nextFloat() * 360f, 0f);
        world.addEntity(creature);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        //harvesters.set(player);
        breakEggs(world, pos, state);
        //harvesters.set(null);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return (state.get(EGGS) > 1 ? bound_several_eggs : bound_one_egg);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(EGGS);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}
