package com.mushroom.midnight.common.world.feature.config;

public class UndergroundPlacementConfig {/*implements IPlacementConfig {
    private final int minCount, maxCount, minHeight, maxHeight;

    public UndergroundPlacementConfig(int minCount, int maxCount, int minHeight, int maxHeight) {
        if (minCount <= maxCount) {
            this.minCount = minCount;
            this.maxCount = maxCount;
        } else {
            this.minCount = maxCount;
            this.maxCount = minCount;
        }
        if (minHeight <= maxHeight) {
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        } else {
            this.minHeight = maxHeight;
            this.maxHeight = minHeight;
        }
    }

    public UndergroundPlacementConfig(int count, int minHeight, int maxHeight) {
        this(count, count, minHeight, maxHeight);
    }

    @Override
    public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        int count = minCount;
        if (minCount != maxCount) {
            count = random.nextInt(maxCount - minCount + 1) + minCount;
        }
        for (int i = 0; i < count; i++) {
            int offsetX = random.nextInt(16) + 8;
            int offsetZ = random.nextInt(16) + 8;
            int maxY = Math.min(world.getSeaLevel(), world.getHeight(chunkOrigin.getX() + offsetX, chunkOrigin.getZ() + offsetZ));
            if (maxY > 0) {
                int currentMaxHeight = Math.min(maxHeight, maxY);
                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(chunkOrigin.add(offsetX, Math.min(minHeight, maxY), offsetZ));
                while (mutablePos.getY() <= currentMaxHeight) {
                    if (world.isAirBlock(mutablePos)) {
                        generator.accept(mutablePos.toImmutable());
                        break;
                    }
                    mutablePos.move(Direction.UP);
                }
            }
        }
    }*/
}
