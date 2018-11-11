package com.mushroom.midnight.common.entity.task;

import com.mushroom.midnight.common.entity.creature.EntityHunter;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityTaskHunterIdle extends EntityAIBase {
    private static final int FLIGHT_HEIGHT = 40;
    private static final int FLIGHT_HEIGHT_DEVIATION = 2;

    private static final int WANDER_RANGE = 40;
    private static final int MIN_WANDER_RANGE = 20;

    private final EntityHunter owner;
    private final double speed;

    public EntityTaskHunterIdle(EntityHunter owner, double speed) {
        this.owner = owner;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return this.owner.getNavigator().noPath();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void startExecuting() {
        Path path = null;

        int retries = 0;
        while (retries++ < 16 && path == null) {
            path = this.produceWanderPath();
        }

        if (path != null) {
            this.owner.getNavigator().setPath(path, this.speed);
        }
    }

    @Nullable
    private Path produceWanderPath() {
        BlockPos flightPos = this.findFlightPos(WANDER_RANGE, MIN_WANDER_RANGE);
        if (flightPos == null) {
            return null;
        }

        return this.owner.getNavigator().getPathToPos(flightPos);
    }

    @Nullable
    private BlockPos findFlightPos(int range, int minRange) {
        // Adapted from RandomPositionGenerator

        Random random = this.owner.getRNG();
        BlockPos homePos = this.owner.getHomePosition();
        boolean nearHome = false;

        if (this.owner.hasHome()) {
            double distanceSq = homePos.distanceSq(this.owner.posX, this.owner.posY, this.owner.posZ) + 4.0;
            double maxHomeDistance = this.owner.getMaximumHomeDistance() + range;
            nearHome = distanceSq < maxHomeDistance * maxHomeDistance;
        }

        BlockPos result = null;
        float maxWeight = -Float.MIN_VALUE;

        for (int i = 0; i < 10; i++) {
            int displacementX = (random.nextBoolean() ? 1 : -1) * (random.nextInt(range - minRange + 1) + minRange);
            int displacementZ = (random.nextBoolean() ? 1 : -1) * (random.nextInt(range - minRange + 1) + minRange);

            if (this.owner.hasHome()) {
                if (this.owner.posX > homePos.getX()) {
                    displacementX -= random.nextInt(range / 2);
                } else {
                    displacementX += random.nextInt(range / 2);
                }

                if (this.owner.posZ > homePos.getZ()) {
                    displacementZ -= random.nextInt(range / 2);
                } else {
                    displacementZ += random.nextInt(range / 2);
                }
            }

            BlockPos displacedPos = new BlockPos(displacementX + this.owner.posX, 0, displacementZ + this.owner.posZ);
            BlockPos target = this.computeFlightTarget(random, displacedPos);

            if (!nearHome || this.owner.isWithinHomeDistanceFromPosition(target)) {
                float weight = this.owner.getBlockPathWeight(target);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    result = target;
                }
            }
        }

        return result;
    }

    private BlockPos computeFlightTarget(Random random, BlockPos displacedPos) {
        int flightHeight = FLIGHT_HEIGHT + random.nextInt(FLIGHT_HEIGHT_DEVIATION) - random.nextInt(FLIGHT_HEIGHT_DEVIATION);
        return new BlockPos(displacedPos.getX(), this.owner.world.getSeaLevel() + flightHeight, displacedPos.getZ());
    }
}
 
