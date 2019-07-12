package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightTileEntities;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MidnightChestBlock extends ChestBlock {
    public MidnightChestModel chestModel;

    public MidnightChestBlock(MidnightChestModel chestModel, Properties properties) {
        super(properties);
        this.chestModel = chestModel;
    }

    public enum MidnightChestModel implements IStringSerializable {
        SHADOWROOT(() -> MidnightBlocks.SHADOWROOT_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST),
        DARK_WILLOW(() -> MidnightBlocks.DARK_WILLOW_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST),
        DEAD_WOOD(() -> MidnightBlocks.DEAD_WOOD_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST),
        NIGHTSHROOM(() -> MidnightBlocks.NIGHTSHROOM_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST),
        DEWSHROOM(() -> MidnightBlocks.DEWSHROOM_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST),
        VIRIDSHROOM(() -> MidnightBlocks.VIRIDSHROOM_CHEST, () -> MidnightTileEntities.MIDNIGHT_CHEST);

        private final Supplier<Block> blockSupplier;
        private final Supplier<TileEntityType<?>> tileSupplier;

        MidnightChestModel(Supplier<Block> blockSupplier, Supplier<TileEntityType<?>> tileSupplier) {
            this.blockSupplier = blockSupplier;
            this.tileSupplier = tileSupplier;
        }

        public Block getBlockType() {
            return this.blockSupplier.get();
        }

        public TileEntityType<?> getTileType() {
            return this.tileSupplier.get();
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public static MidnightChestModel getDefault() {
            return SHADOWROOT;
        }

        public static MidnightChestModel getModel(TileEntityType<?> tileType) {
            for (MidnightChestModel currentChestModel : MidnightChestModel.values()) {
                if (currentChestModel.getTileType().equals(tileType)) {
                    return currentChestModel;
                }
            }
            return getDefault();
        }
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader world) {
        return MidnightTileEntities.MIDNIGHT_CHEST.create();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
