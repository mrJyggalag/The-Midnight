package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SuavisBlock extends Block implements IGrowable {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);
    protected static final AxisAlignedBB[] bounds = new AxisAlignedBB[] {
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.2125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.4375d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.8125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d),
    };

    public SuavisBlock() {
        super(Material.GOURD, MaterialColor.LIGHT_BLUE);
        setLightLevel(0.8F);
        setCreativeTab(MidnightItemGroups.DECORATION);
        setHardness(1f);
        setSoundType(SoundType.SLIME);
        setDefaultState(getStateContainer().getBaseState().with(STAGE, 3));
        setTickRandomly(true);
    }

    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, EnumHand hand) {
        return getDefaultState();
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (!world.isRemote && fallDistance > 0.8f && entity instanceof LivingEntity) {
            BlockState state = world.getBlockState(pos);
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
            boolean isFirstStage = state.get(STAGE) == 0;
            if (!isFirstStage && world.rand.nextInt(100) == 0) {
                world.destroyBlock(pos, true);
            } else {
                spawnAsEntity(world, pos, new ItemStack(MidnightItems.RAW_SUAVIS));
                world.setBlockState(pos, isFirstStage ? Blocks.AIR.getDefaultState() : state.with(STAGE, state.get(STAGE) - 1), 2);
                world.playEvent(2001, pos, getStateId(state));
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.RAW_SUAVIS;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(MidnightItems.RAW_SUAVIS);
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return state.get(STAGE) == 3;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
        return face == Direction.DOWN || state.get(STAGE) == 3 ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, meta & 3);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(STAGE);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return state.get(STAGE) == 3;
    }

    @Override
    public boolean canSilkHarvest() {
        return true;
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);
        harvesters.set(player);
        dropBlockAsItem(world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
        harvesters.set(null);
        if (!world.isRemote && !player.isCreative() && player instanceof ServerPlayerEntity) {
            MidnightCriterion.HARVESTED_SUAVIS.trigger((ServerPlayerEntity) player);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, BlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
        if (!world.isRemote) {
            PlayerEntity player = harvesters.get();
            if (player == null || (!player.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)) {
                createNauseaCloud(world, pos, state.get(STAGE));
            }
        }
    }

    private static void createNauseaCloud(World world, BlockPos pos, int intensity) {
        AreaEffectCloudEntity entity = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());
        entity.setRadius(1.5f + 0.5f * intensity);
        entity.setRadiusOnUse(-0.3f);
        entity.setWaitTime(10);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(PotionTypes.EMPTY);
        entity.setColor(0x355796);
        entity.addEffect(new EffectInstance(Effects.NAUSEA, 20 * (intensity + 1) * 6, 0, false, true));
        world.addEntity(entity);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
        boolean isSilkTouch = harvesters.get() != null && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, harvesters.get().getHeldItemMainhand()) > 0;
        int stage = state.get(STAGE);
        if (isSilkTouch && stage == 3) {
            drops.add(new ItemStack(this));
            return;
        }
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int count = isSilkTouch ? stage + 1 : quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(state, rand, fortune);
            if (item != Items.AIR) {
                drops.add(new ItemStack(item, 1, damageDropped(state)));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader worldIn, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(STAGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(STAGE, state.get(STAGE) + 1), 2);
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        if (!isSideSolid(world.getBlockState(pos.down()), world, pos, Direction.UP)) {
            world.destroyBlock(pos, true);
        } else {
            if (state.get(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
            }
        }
    }

    @Override
    public int quantityDropped(BlockState state, int fortune, Random random) {
        int maxSlice = state.get(STAGE) + 1;
        int minSlice = Math.min(1 + fortune, maxSlice);
        return random.nextInt(maxSlice - minSlice + 1) + minSlice;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return getBoundingBox(state, world, pos);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return bounds[state.get(STAGE)];
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getValue(STAGE) < 2;
    }
}
