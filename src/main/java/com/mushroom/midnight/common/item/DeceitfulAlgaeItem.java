package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DeceitfulAlgaeItem extends BlockItem {
    public DeceitfulAlgaeItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (rayTraceResult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, heldItem);
        }

        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockHit = (BlockRayTraceResult) rayTraceResult;
            BlockPos pos = blockHit.getPos();
            Direction direction = blockHit.getFace();

            if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(direction), direction, heldItem)) {
                return new ActionResult<>(ActionResultType.FAIL, heldItem);
            }

            BlockPos placePos = pos.up();
            IFluidState fluidState = world.getFluidState(pos);

            if (fluidState.getFluid().isIn(FluidTags.WATER) && world.isAirBlock(placePos)) {
                // special case for handling block placement with water lilies
                net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, placePos);
                world.setBlockState(placePos, MidnightBlocks.DECEITFUL_ALGAE.getDefaultState(), 11);
                if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, blocksnapshot, net.minecraft.util.Direction.UP)) {
                    blocksnapshot.restore(true, false);
                    return new ActionResult<ItemStack>(ActionResultType.FAIL, heldItem);
                }

                if (player instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, placePos, heldItem);
                }

                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }

                player.addStat(Stats.ITEM_USED.get(this));

                world.playSound(player, pos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
            }
        }

        return new ActionResult<>(ActionResultType.FAIL, heldItem);
    }
}
