package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.network.RockshroomBrokenMessage;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.Random;

public class RockshroomBlock extends Block {
    private static final int SPORE_COUNT = 32;
    private static final double SPORE_SPEED = 0.3;

    private static final double DAMAGE_RANGE = 4.0;

    private static final DamageSource ROCKSHROOM_SPORE = new MidnightDamageSource("rockshroom_spore");

    public RockshroomBlock() {
        super(Material.ROCK, MapColor.PINK_STAINED_HARDENED_CLAY);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest) {
        boolean result = super.removedByPlayer(state, world, pos, player, willHarvest);
        this.onBroken(world, pos, player);

        return result;
    }

    private void onBroken(World world, BlockPos pos, PlayerEntity player) {
        ItemStack heldItem = player.getHeldItemMainhand();
        if (player.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem) > 0) {
            return;
        }

        if (!world.isRemote) {
            NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 0.0);
            Midnight.NETWORK.sendToAllTracking(new RockshroomBrokenMessage(pos), point);
            this.damagePlayer(world, pos, player);
        }
    }

    private void damagePlayer(World world, BlockPos pos, PlayerEntity player) {
        Vec3d origin = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        Vec3d target = player.getPositionEyes(1.0F);
        if (target.subtract(origin).lengthSquared() > DAMAGE_RANGE * DAMAGE_RANGE) {
            return;
        }

        RayTraceResult rayTrace = world.rayTraceBlocks(origin, target);
        if (rayTrace == null || rayTrace.typeOfHit == RayTraceResult.Type.MISS) {
            player.attackEntityFrom(ROCKSHROOM_SPORE, world.rand.nextFloat() * 3.5F + 0.5F);
        }
    }

    public void spawnSpores(World world, BlockPos pos) {
        Random random = world.rand;
        for (int i = 0; i < SPORE_COUNT; i++) {
            Vec3d direction = new Vec3d(
                    random.nextFloat() * 2.0F - 1.0F,
                    random.nextFloat() * 2.0F - 1.0F,
                    random.nextFloat() * 2.0F - 1.0F
            ).normalize();

            double x = pos.getX() + 0.5 + direction.x * 0.4;
            double y = pos.getY() + 0.5 + direction.y * 0.4;
            double z = pos.getZ() + 0.5 + direction.z * 0.4;

            Vec3d vel = direction.scale(SPORE_SPEED);
            MidnightParticles.SPORE.spawn(world, x, y, z, vel.x, vel.y + 0.05, vel.z);
        }
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(MidnightBlocks.ROCKSHROOM);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.ROCKSHROOM_CLUMP;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(2) + 2;
    }
}
