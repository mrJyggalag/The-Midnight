package com.mushroom.midnight.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    public RiftAttachment fixedToSurface(World world) {
        BlockPos surfacePos = new BlockPos(pos.getX(), world.getChunk(pos).getHeight(), pos.getZ());
        return new RiftAttachment(surfacePos, this.yaw);
    }

    public void apply(RiftEntity rift) {
        rift.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.yaw, 0.0F);
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        compound.putInt("x", this.pos.getX());
        compound.putInt("y", this.pos.getY());
        compound.putInt("z", this.pos.getZ());
        compound.putFloat("yaw", this.yaw);
        return compound;
    }

    public static RiftAttachment deserialize(CompoundNBT compound) {
        BlockPos pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
        float yaw = compound.getFloat("yaw");
        return new RiftAttachment(pos, yaw);
    }
}
