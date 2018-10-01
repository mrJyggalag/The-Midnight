package com.mushroom.midnight.common.blocks;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.container.ContainerShadowrootCT;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockShadowrootCraftingTable extends Block implements IModelProvider {

    public BlockShadowrootCraftingTable() {
        super(Material.WOOD);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            playerIn.displayGui(new BlockShadowrootCraftingTable.InterfaceCraftingTable(worldIn, pos));
           // playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            return true;
        }
    }

    public static class InterfaceCraftingTable implements IInteractionObject
    {
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(World worldIn, BlockPos pos)
        {
            System.out.println("All good");
            this.world = worldIn;
            this.position = pos;
        }

        @Override
        public String getName()
        {
            return "crafting_table";
        }

        @Override
        public boolean hasCustomName()
        {
            return true;
        }

        @Override
        public ITextComponent getDisplayName()
        {
            return new TextComponentTranslation(ModBlocks.SHADOWROOT_CRAFTING_TABLE.getTranslationKey() + ".name", new Object[0]);
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerShadowrootCT(playerInventory, this.world, this.position);
        }

        @Override
        public String getGuiID()
        {
            return "minecraft:crafting_table";
        }
    }
}
