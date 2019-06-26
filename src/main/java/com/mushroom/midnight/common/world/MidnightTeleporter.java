package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftTravelCooldown;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.registry.MidnightArmorMaterials;
import com.mushroom.midnight.common.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.util.ITeleporter;

public class MidnightTeleporter implements ITeleporter {
    public static final int COOLDOWN = 40;

    private final RiftEntity originRift;

    public MidnightTeleporter(RiftEntity originRift) {
        this.originRift = originRift;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        RiftBridge bridge = this.originRift.getBridge();
        if (bridge == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Bridge not present on portal {}", this.originRift);
            return;
        }

        if (entity instanceof PlayerEntity && !EntityUtil.isCoveredBy((LivingEntity) entity, MidnightArmorMaterials.TENEBRUM)) {
            bridge.close();
        }

        RiftEntity endpointRift = bridge.computeEndpoint(world.provider.getDimensionType());
        if (endpointRift == null) {
            Midnight.LOGGER.warn("Unable to teleport entity through rift! Endpoint not present from portal {}", this.originRift);
            return;
        }

        Vec3d placementPos = this.findPlacementPos(world, entity, endpointRift);

        entity.posX = placementPos.x + 0.5;
        entity.posY = placementPos.y + 0.5;
        entity.posZ = placementPos.z + 0.5;
        entity.fallDistance = 0.0F;

        RiftTravelCooldown capability = entity.getCapability(Midnight.RIFT_TRAVEL_COOLDOWN_CAP, null);
        if (capability != null) {
            capability.setCooldown(COOLDOWN);
        }
    }

    private Vec3d findPlacementPos(World world, Entity entity, RiftEntity endpointRift) {
        float angle = (float) Math.toRadians(entity.rotationYaw);
        float displacementX = -MathHelper.sin(angle) * endpointRift.getWidth() / 2.0F;
        float displacementZ = MathHelper.cos(angle) * endpointRift.getWidth() / 2.0F;

        Vec3d placementPos = new Vec3d(endpointRift.posX + displacementX, endpointRift.posY + 0.5, endpointRift.posZ + displacementZ);
        if (!world.checkBlockCollision(this.getEntityBoundAt(entity, placementPos))) {
            return placementPos;
        }

        BlockPos placementBlockPos = new BlockPos(placementPos.x, placementPos.y, placementPos.z);
        BlockPos minPos = placementBlockPos.add(-2, -2, -2);
        BlockPos maxPos = placementBlockPos.add(2, 2, 2);
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            Vec3d originPos = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            AxisAlignedBB entityBound = this.getEntityBoundAt(entity, originPos);
            if (entityBound.intersects(endpointRift.getBoundingBox())) {
                continue;
            }
            if (!world.checkBlockCollision(entityBound)) {
                return originPos;
            }
        }

        BlockPos surface = world.getHeight(Heightmap.Type.MOTION_BLOCKING, placementBlockPos);
        return new Vec3d(placementPos.x, surface.getY(), placementPos.z);
    }

    private AxisAlignedBB getEntityBoundAt(Entity entity, Vec3d pos) {
        float halfWidth = entity.getWidth() / 2.0F;
        return new AxisAlignedBB(
                pos.x - halfWidth, pos.y, pos.z - halfWidth,
                pos.x + halfWidth, pos.y + entity.getHeight(), pos.z + halfWidth
        );
    }
}
