package com.mushroom.midnight.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DeceitfulAlgaeItem extends ItemBlock {
    public DeceitfulAlgaeItem(Block block) {
        super(block);
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, EnumHand hand, Direction facing, float hitX, float hitY, float hitZ) {
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult result = this.rayTrace(world, player, true);
        if (result == null) {
            return new ActionResult<>(ActionResultType.PASS, stack);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            Direction sideHit = result.sideHit;
            float hitX = (float) (result.hitVec.x - pos.getX());
            float hitY = (float) (result.hitVec.y - pos.getY());
            float hitZ = (float) (result.hitVec.z - pos.getZ());

            if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(sideHit), sideHit, stack)) {
                return new ActionResult<>(ActionResultType.FAIL, stack);
            }

            BlockState state = world.getBlockState(pos);
            BlockPos placePos = pos.up();

            if (state.getMaterial() == Material.WATER && state.get(BlockLiquid.LEVEL) == 0 && world.isAirBlock(placePos)) {
                BlockState placeState = this.block.getStateForPlacement(world, placePos, sideHit, hitX, hitY, hitZ, 0, player, hand);

                if (this.placeBlockAt(stack, player, world, placePos, sideHit, hitX, hitY, hitZ, placeState)) {
                    this.applyPlaceEffect(world, player, placePos);

                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }

                    player.swingArm(hand);
                    return new ActionResult<>(ActionResultType.SUCCESS, stack);
                }
            }
        }

        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    private void applyPlaceEffect(World world, PlayerEntity player, BlockPos pos) {
        BlockState placedState = world.getBlockState(pos);

        SoundType soundType = placedState.getBlock().getSoundType(placedState, world, pos, player);
        world.playSound(player, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
    }
}
