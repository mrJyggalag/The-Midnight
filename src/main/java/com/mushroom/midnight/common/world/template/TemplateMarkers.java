package com.mushroom.midnight.common.world.template;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mushroom.midnight.Midnight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

public class TemplateMarkers {
    private final Multimap<String, BlockPos> markers;
    private final Map<BlockPos, BlockState> replacements;

    private TemplateMarkers(Multimap<String, BlockPos> markers, Map<BlockPos, BlockState> replacements) {
        this.markers = markers;
        this.replacements = replacements;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void forEach(BiConsumer<? super String, ? super BlockPos> action) {
        this.markers.forEach(action);
    }

    public void forEachReplacement(BiConsumer<? super BlockPos, ? super BlockState> action) {
        this.replacements.forEach(action);
    }

    @Nullable
    public BlockPos lookupAny(String key) {
        Iterator<BlockPos> iterator = this.lookup(key).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public Collection<BlockPos> lookup(String key) {
        return this.markers.get(key);
    }

    public static class Builder {
        private final ImmutableMultimap.Builder<String, BlockPos> markers = ImmutableMultimap.builder();
        private final ImmutableMap.Builder<BlockPos, BlockState> replacements = ImmutableMap.builder();

        private Builder() {
        }

        public Builder add(BlockPos pos, String value) {
            String[] tokens = value.split("=", 2);

            String key = tokens[0];
            this.markers.put(key, pos);

            if (tokens.length > 1) {
                String replacementString = tokens[1];
                Block replacementBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(replacementString));
                if (replacementBlock != null) {
                    this.replacements.put(pos, replacementBlock.getDefaultState());
                } else {
                    Midnight.LOGGER.error("Invalid replacement marker '{}'", replacementString);
                }
            }

            return this;
        }

        public TemplateMarkers build() {
            return new TemplateMarkers(this.markers.build(), this.replacements.build());
        }
    }
}
