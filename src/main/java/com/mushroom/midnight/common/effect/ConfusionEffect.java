package com.mushroom.midnight.common.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ConfusionEffect extends GenericEffect {
    public ConfusionEffect() {
        super(true, 0x0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            if (entityLiving.getRNG().nextInt(5) == 0) {
                List<Entity> list = entityLiving.world.getEntitiesInAABBexcluding(entityLiving, entityLiving.getEntityBoundingBox().grow(2d), p -> p instanceof EntityLiving && p.isEntityAlive());
                if (list.isEmpty()) {
                    entityLiving.setAttackTarget(null);
                    entityLiving.motionX = entityLiving.motionY = entityLiving.motionZ = 0d;
                } else {
                    entityLiving.setAttackTarget((EntityLivingBase) list.get(entityLiving.world.rand.nextInt(list.size())));
                }
            } else if (entityLiving.getAttackTarget() == null) {
                entityLiving.motionX = entityLiving.motionY = entityLiving.motionZ = 0f;
            }
        } else if (entity instanceof EntityPlayer && entity.getRNG().nextBoolean()) {
            entity.setPositionAndRotation(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ, entity.getRNG().nextFloat() * 360f, entity.rotationPitch);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
