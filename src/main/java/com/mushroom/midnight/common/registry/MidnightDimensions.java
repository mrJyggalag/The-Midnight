package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.MidnightDimension;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class MidnightDimensions {
    public static final DimensionType MIDNIGHT = new Builder()
            .withName(new ResourceLocation(Midnight.MODID, "midnight"))
            .withFactory(MidnightDimension::new)
            .register();

    private static class Builder {
        private ResourceLocation name;
        private BiFunction<World, DimensionType, ? extends Dimension> factory;
        private PacketBuffer data = new PacketBuffer(Unpooled.buffer(0));
        private boolean hasSkylight;

        public Builder withName(ResourceLocation name) {
            this.name = name;
            return this;
        }

        public Builder withFactory(BiFunction<World, DimensionType, ? extends Dimension> factory) {
            this.factory = factory;
            return this;
        }

        public Builder withData(PacketBuffer data) {
            this.data = data;
            return this;
        }

        public Builder withSkylight() {
            this.hasSkylight = true;
            return this;
        }

        public DimensionType register() {
            return DimensionManager.registerDimension(this.name, new ModDimension() {
                @Override
                public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
                    return Builder.this.factory;
                }
            }, this.data, this.hasSkylight);
        }
    }
}
