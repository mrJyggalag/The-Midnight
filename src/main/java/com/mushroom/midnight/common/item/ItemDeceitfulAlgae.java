package com.mushroom.midnight.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemDeceitfulAlgae extends ItemBlock {
    public ItemDeceitfulAlgae(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult result = this.rayTrace(world, player, true);
        if (result == null) {
            return new ActionResult<>(EnumActionResult.PASS, stack);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            EnumFacing sideHit = result.sideHit;
            float hitX = (float) (result.hitVec.x - pos.getX());
            float hitY = (float) (result.hitVec.y - pos.getY());
            float hitZ = (float) (result.hitVec.z - pos.getZ());

            if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(sideHit), sideHit, stack)) {
                return new ActionResult<>(EnumActionResult.FAIL, stack);
            }

            IBlockState state = world.getBlockState(pos);
            BlockPos placePos = pos.up();

            if (state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0 && world.isAirBlock(placePos)) {
                IBlockState placeState = this.block.getStateForPlacement(world, placePos, sideHit, hitX, hitY, hitZ, 0, player, hand);

                if (this.placeBlockAt(stack, player, world, placePos, sideHit, hitX, hitY, hitZ, placeState)) {
                    this.applyPlaceEffect(world, player, placePos);
                    stack.shrink(1);

                    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
                }
            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    private void applyPlaceEffect(World world, EntityPlayer player, BlockPos pos) {
        IBlockState placedState = world.getBlockState(pos);

        SoundType soundType = placedState.getBlock().getSoundType(placedState, world, pos, player);
        world.playSound(player, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
    }
}
