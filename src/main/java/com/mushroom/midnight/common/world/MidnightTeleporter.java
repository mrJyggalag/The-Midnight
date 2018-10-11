package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.RiftBridge;
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
        RiftBridge bridge = this.originRift.getBridge();
        if (bridge == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Bridge not present on portal {}", this.originRift);
            return;
        }

        if (entity instanceof EntityPlayer) {
            bridge.close();
        }

        EntityRift endpointRift = bridge.computeEndpoint(world.provider.getDimensionType());
        if (endpointRift == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Endpoint not present from portal {}", this.originRift);
            return;
        }

        float angle = (float) Math.toRadians(entity.rotationYaw);
        // TODO: Find safe space to place player around rift
        float displacementX = -MathHelper.sin(angle) * endpointRift.width / 2.0F;
        float displacementZ = MathHelper.cos(angle) * endpointRift.width / 2.0F;

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
