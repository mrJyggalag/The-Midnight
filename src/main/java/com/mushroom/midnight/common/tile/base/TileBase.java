package com.mushroom.midnight.common.tile.base;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileBase extends TileEntity {

    public TileBase(TileEntityType<?> entityType) {
        super(entityType);
    }

    public void notifyUpdate() {
        getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, serializeNBT());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public final void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public final CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public final void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }
}
