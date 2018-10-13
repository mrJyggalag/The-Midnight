package com.mushroom.midnight.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class RiftAttachment {
    private final BlockPos pos;
    private final float yaw;

    public RiftAttachment(BlockPos pos, float yaw) {
        this.pos = pos;
        this.yaw = yaw;
    }

    public static RiftAttachment read(ByteBuf buf) {
        BlockPos pos = BlockPos.fromLong(buf.readLong());
        float yaw = buf.readFloat();
        return new RiftAttachment(pos, yaw);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void write(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeFloat(this.yaw);
    }

    public void apply(EntityRift rift) {
        BlockPos pos = rift.world.getTopSolidOrLiquidBlock(this.pos);
        rift.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.yaw, 0.0F);
    }

    public NBTTagCompound serialize(NBTTagCompound compound) {
        compound.setInteger("x", this.pos.getX());
        compound.setInteger("y", this.pos.getY());
        compound.setInteger("z", this.pos.getZ());
        compound.setFloat("yaw", this.yaw);
        return compound;
    }

    public static RiftAttachment deserialize(NBTTagCompound compound) {
        BlockPos pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
        float yaw = compound.getFloat("yaw");
        return new RiftAttachment(pos, yaw);
    }
}
