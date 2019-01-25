package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockSuavis extends Block implements IModelProvider, IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);
    protected static final AxisAlignedBB[] bounds = new AxisAlignedBB[] {
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.4375d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.8125d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d),
    };

    public BlockSuavis() {
        super(Material.GOURD, MapColor.LIGHT_BLUE);
        setLightLevel(0.8F);
        setCreativeTab(Midnight.DECORATION_TAB);
        setHardness(1f);
        setSoundType(SoundType.SLIME);
        setDefaultState(blockState.getBaseState().withProperty(STAGE, 3));
        setTickRandomly(true);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState();
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
            if (state.getValue(STAGE) == 0 || world.rand.nextInt(100) == 0) {
                world.destroyBlock(pos, true);
            } else {
                spawnAsEntity(world, pos, new ItemStack(ModItems.RAW_SUAVIS));
                world.setBlockState(pos, state.withProperty(STAGE, state.getValue(STAGE) - 1), 2);
                world.playEvent(2001, pos, getStateId(state));
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.RAW_SUAVIS;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return state.getValue(STAGE) == 3;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN || state.getValue(STAGE) == 3 ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, meta & 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(STAGE) == 3;
    }

    @Override
    public boolean canSilkHarvest() {
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);
        harvesters.set(player);
        dropBlockAsItem(world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
        harvesters.set(null);
        if (!world.isRemote && !player.isCreative() && player instanceof EntityPlayerMP) {
            ModCriterion.HARVESTED_SUAVIS.trigger((EntityPlayerMP) player);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
        if (!world.isRemote) {
            EntityPlayer player = harvesters.get();
            if (player == null || (!player.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)) {
                createNauseaCloud(world, pos, state.getValue(STAGE));
            }
        }
    }

    private static void createNauseaCloud(World world, BlockPos pos, int intensity) {
        EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());
        entity.setRadius(3.0F);
        entity.setRadiusOnUse(-0.5F);
        entity.setWaitTime(10);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(PotionTypes.EMPTY);
        entity.setColor(0x355796);
        entity.addEffect(new PotionEffect(MobEffects.NAUSEA, 20 * (intensity + 1) * 6, 0, false, true));
        world.spawnEntity(entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.withProperty(STAGE, state.getValue(STAGE) + 1), 2);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!isSideSolid(world.getBlockState(pos.down()), world, pos, EnumFacing.UP)) {
            world.destroyBlock(pos, true);
        } else {
            if (state.getValue(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
            }
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int maxSlice = state.getValue(STAGE) + 1;
        int minSlice = Math.min(1 + fortune, maxSlice);
        return random.nextInt(maxSlice - minSlice + 1) + minSlice;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getBoundingBox(state, world, pos);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return bounds[state.getValue(STAGE)];
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getValue(STAGE) < 2;
    }
}
