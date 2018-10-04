package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.entities.EntityRift;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class MidnightTeleporter implements ITeleporter {
    public static final int COOLDOWN = 40;

    private final EntityRift originRift;

    public MidnightTeleporter(EntityRift originRift) {
        this.originRift = originRift;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        EntityRift endpointRift = this.originRift.computeEndpointRift(world);

        if (entity instanceof EntityPlayer) {
            this.originRift.close();
            endpointRift.close();
        }

        float angle = (float) Math.toRadians(entity.rotationYaw);
        // TODO: Find safe space to place player around rift
        float displacementX = -MathHelper.sin(angle) * endpointRift.width;
        float displacementZ = MathHelper.cos(angle) * endpointRift.width;

        entity.posX = endpointRift.posX + displacementX;
        entity.posY = endpointRift.posY + 0.5F;
        entity.posZ = endpointRift.posZ + displacementZ;
        entity.fallDistance = 0.0F;

        RiftCooldownCapability capability = entity.getCapability(Midnight.riftCooldownCap, null);
        if (capability != null) {
            capability.setCooldown(COOLDOWN);
        }
    }
}
