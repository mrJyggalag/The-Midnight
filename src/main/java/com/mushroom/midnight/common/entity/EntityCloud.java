package com.mushroom.midnight.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.List;

public class EntityCloud extends EntityAreaEffectCloud {
    private boolean allowTeleport = false;

    public EntityCloud(World world) {
        super(world);
    }

    public EntityCloud(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityCloud setAllowTeleport() {
        this.allowTeleport = true;
        return this;
    }

    @Override
    public void onUpdate() {
        // TODO could be a move
        if (this.allowTeleport && !this.world.isRemote && isEntityAlive() && this.world.getTotalWorldTime() % 40 == 0) {
            List<Entity> list = this.world.getEntitiesInAABBexcluding(getOwner(), getEntityBoundingBox().grow(5d), p -> p instanceof EntityLivingBase && p.isEntityAlive());
            if (!list.isEmpty()) {
                Entity entity = list.get(this.world.rand.nextInt(list.size()));
                setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
            }
        }
        super.onUpdate();
    }
}
