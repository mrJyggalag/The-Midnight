package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockBasic extends Block implements IModelProvider {
    private boolean glow;

    public BlockBasic(Material material) {
        super(material);
        this.setCreativeTab(Midnight.BUILDING_TAB);
    }

    public BlockBasic withHarvestLevel(String tool, int level) {
        this.setHarvestLevel(tool, level);
        return this;
    }

    public BlockBasic withSoundType(SoundType sound) {
        super.setSoundType(sound);
        return this;
    }

    public BlockBasic withGlow() {
        this.glow = true;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (this.glow) {
            int skyLight = 15;
            int blockLight = 15;
            return skyLight << 20 | blockLight << 4;
        }
        return super.getPackedLightmapCoords(state, source, pos);
    }
}
