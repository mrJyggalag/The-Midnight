package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.container.ContainerShadowrootCT;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ShadowrootCraftingTableBlock extends Block {

    public ShadowrootCraftingTableBlock() {
        super(Material.WOOD);
        this.setHardness(2.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MidnightItemGroups.DECORATION);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            player.displayGui(new ShadowrootCraftingTableBlock.InterfaceCraftingTable(world, pos));
            // playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            return true;
        }
    }

    public static class InterfaceCraftingTable implements IInteractionObject {
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(World worldIn, BlockPos pos) {
            this.world = worldIn;
            this.position = pos;
        }

        @Override
        public String getName() {
            return "crafting_table";
        }

        @Override
        public boolean hasCustomName() {
            return true;
        }

        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent(MidnightBlocks.SHADOWROOT_CRAFTING_TABLE.getTranslationKey() + ".name", new Object[0]);
        }

        @Override
        public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
            return new ContainerShadowrootCT(playerInventory, this.world, this.position);
        }

        @Override
        public String getGuiID() {
            return "minecraft:crafting_table";
        }
    }
}
