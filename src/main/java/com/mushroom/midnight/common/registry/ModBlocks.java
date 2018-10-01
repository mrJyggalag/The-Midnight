package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.blocks.BlockShadowroot;
import com.mushroom.midnight.common.blocks.BlockShadowrootLeaves;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModBlocks {

    static List<Block> blocks;

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks = Lists.newArrayList(
                new BlockShadowroot("shadowroot"),
                new BlockShadowrootLeaves("shadowroot_leaves")
        );

        //registerTile(TileStatue.class, "statue");

        event.getRegistry().registerAll(blocks.toArray(new Block[0]));
    }

    private static void registerTile(Class classs, String registryName) {
        GameRegistry.registerTileEntity(classs, Midnight.MODID + ":tile_" + registryName);
    }

}