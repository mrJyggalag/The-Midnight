package com.mushroom.midnight.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityRift extends Entity {
    private long geometrySeed = new Random().nextLong();
    private RiftGeometry geometry;

    public EntityRift(World world) {
        super(world);
        this.setSize(1.8F, 4.0F);
        this.noClip = true;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote && this.geometry == null) {
            this.geometry = RiftGeometry.generate(new Random(this.geometrySeed), this.width / 2.0F, this.height / 2.0F);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.geometrySeed = compound.getLong("geometry_seed");
        this.geometry = null;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setLong("geometry_seed", this.geometrySeed);
    }

    @Nullable
    public RiftGeometry getGeometry() {
        return this.geometry;
    }
}
