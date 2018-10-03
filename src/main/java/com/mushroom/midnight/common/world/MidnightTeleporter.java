package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.entities.EntityRift;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public class MidnightTeleporter implements ITeleporter {
    public static final int COOLDOWN = 40;
    public static final MidnightTeleporter INSTANCE = new MidnightTeleporter();

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        EntityRift rift = this.getPlaceRift(world, entity);

        float angle = (float) Math.toRadians(rift.rotationYaw);
        // TODO: Find safe space to place player around rift
        float displacementX = -MathHelper.sin(angle) * rift.width;
        float displacementZ = MathHelper.cos(angle) * rift.width;

        entity.posX = rift.posX + displacementX;
        entity.posY = rift.posY + rift.height / 2.0F;
        entity.posZ = rift.posZ + displacementZ;
        entity.fallDistance = 0.0F;

        RiftCooldownCapability capability = entity.getCapability(Midnight.riftCooldownCap, null);
        if (capability != null) {
            capability.setCooldown(COOLDOWN);
        }
    }

    @Nonnull
    private EntityRift getPlaceRift(World world, Entity entity) {
        BlockPos surface = world.getTopSolidOrLiquidBlock(entity.getPosition());
        AxisAlignedBB bounds = new AxisAlignedBB(surface).grow(16.0);

        List<EntityRift> rifts = world.getEntitiesWithinAABB(EntityRift.class, bounds);
        rifts.sort(Comparator.comparingDouble(e -> e.getDistanceSq(entity)));

        if (!rifts.isEmpty()) {
            return rifts.get(0);
        }

        EntityRift rift = new EntityRift(world);
        rift.setPositionAndRotation(surface.getX() + 0.5, surface.getY() + 1.0, surface.getZ() + 0.5, entity.rotationYaw, 0.0F);
        rift.openProgress = EntityRift.OPEN_TIME;
        world.spawnEntity(rift);

        return rift;
    }
}
