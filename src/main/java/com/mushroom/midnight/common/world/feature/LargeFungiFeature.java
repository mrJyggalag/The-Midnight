package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockMidnightFungiHat;
import com.mushroom.midnight.common.block.BlockMidnightFungiShelf;
import com.mushroom.midnight.common.block.BlockMidnightFungiStem;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LargeFungiFeature extends MidnightNaturalFeature {
    private static final Block[] SHELF_BLOCKS = new Block[] { ModBlocks.NIGHTSHROOM_SHELF, ModBlocks.DEWSHROOM_SHELF, ModBlocks.VIRIDSHROOM_SHELF };
    private static final int SHELF_ATTACH_CHANCE = 6;

    private final IBlockState stem;
    private final IBlockState hat;

    public LargeFungiFeature(IBlockState stem, IBlockState hat) {
        this.stem = stem;
        this.hat = hat;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        if (!this.canGrow(world, origin.down())) {
            return false;
        }

        FungiShape[] shapes = FungiShape.values();
        FungiShape shape = shapes[rand.nextInt(shapes.length)];

        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();

        PlacementSettings placementSettings = this.buildPlacementSettings(rand);

        ResourceLocation stemTemplateId = shape.getStemTemplate(rand);
        ResourceLocation hatTemplateId = shape.getHatTemplate(rand);

        Template stemTemplate = templateManager.getTemplate(server, stemTemplateId);
        Template hatTemplate = templateManager.getTemplate(server, hatTemplateId);

        BlockPos correctedOrigin = this.computeCorrectedOrigin(origin, stemTemplate, placementSettings);
        if (correctedOrigin == null) {
            Midnight.LOGGER.warn("Template '{}' did not have required 'origin' data block", hatTemplateId);
            return false;
        }

        Map<BlockPos, String> stemData = stemTemplate.getDataBlocks(correctedOrigin, placementSettings);
        BlockPos hatAttachment = this.reverseLookup(stemData, "hat");
        if (hatAttachment == null) {
            Midnight.LOGGER.warn("Template '{}' did not have required 'hat' data block", stemTemplateId);
            return false;
        }

        BlockPos hatOrigin = this.computeCorrectedOrigin(hatAttachment, hatTemplate, placementSettings);
        if (hatOrigin == null) {
            Midnight.LOGGER.warn("Template '{}' did not have required 'origin' data block", hatTemplateId);
            return false;
        }

        BlockPos minPos = WorldUtil.min(correctedOrigin, hatAttachment);
        BlockPos maxPos = WorldUtil.max(correctedOrigin, hatAttachment);
        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            if (!this.canReplace(world, pos)) {
                return false;
            }
        }

        stemTemplate.addBlocksToWorld(world, correctedOrigin, this::processState, placementSettings, 2 | 16);
        hatTemplate.addBlocksToWorld(world, hatOrigin, this::processState, placementSettings, 2 | 16);

        Map<BlockPos, String> globalData = new HashMap<>(stemData);
        globalData.putAll(hatTemplate.getDataBlocks(hatOrigin, placementSettings));

        this.processDataBlocks(world, globalData);
        this.attachShelfBlocks(world, rand, hatOrigin, hatTemplate, placementSettings);

        return true;
    }

    private boolean canGrow(World world, BlockPos groundPos) {
        // TODO: Delegate to plant
        IBlockState groundState = world.getBlockState(groundPos);
        Block groundBlock = groundState.getBlock();
        return groundBlock == ModBlocks.MIDNIGHT_GRASS || groundBlock == ModBlocks.MIDNIGHT_DIRT
                || groundBlock == ModBlocks.NIGHTSTONE
                || groundBlock == Blocks.MYCELIUM;
    }

    private Template.BlockInfo processState(World world, BlockPos pos, Template.BlockInfo info) {
        IBlockState state = info.blockState;
        if (state.getBlock() instanceof BlockMidnightFungiStem) {
            return new Template.BlockInfo(pos, this.stem, null);
        } else if (state.getBlock() instanceof BlockMidnightFungiHat) {
            return new Template.BlockInfo(pos, this.hat, null);
        } else if (state.getBlock() == Blocks.STRUCTURE_BLOCK || state.getBlock() == Blocks.AIR) {
            return null;
        }
        return info;
    }

    private void attachShelfBlocks(World world, Random random, BlockPos hatOrigin, Template hatTemplate, PlacementSettings placementSettings) {
        for (Template.BlockInfo block : hatTemplate.blocks) {
            IBlockState state = block.blockState;
            if (state.getBlock() instanceof BlockMidnightFungiHat) {
                if (random.nextInt(SHELF_ATTACH_CHANCE) == 0) {
                    BlockPos transformedPos = Template.transformedBlockPos(placementSettings, block.pos).add(hatOrigin);
                    this.attachShelf(world, random, transformedPos);
                }
            }
        }
    }

    private void attachShelf(World world, Random random, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        List<EnumFacing> attachSides = BlockMidnightFungiHat.getOuterSides(state.getActualState(world, pos));
        attachSides.remove(EnumFacing.DOWN);

        if (!attachSides.isEmpty()) {
            Block shelfBlock = SHELF_BLOCKS[random.nextInt(SHELF_BLOCKS.length)];
            EnumFacing attachSide = attachSides.get(random.nextInt(attachSides.size()));

            BlockPos offsetPos = pos.offset(attachSide);
            if (this.canReplace(world, offsetPos)) {
                world.setBlockState(offsetPos, shelfBlock.getDefaultState()
                        .withProperty(BlockMidnightFungiShelf.FACING, attachSide)
                );
            }
        }
    }

    private void processDataBlocks(World world, Map<BlockPos, String> globalData) {
        for (Map.Entry<BlockPos, String> entry : globalData.entrySet()) {
            BlockPos pos = entry.getKey();
            String tag = entry.getValue();
            if (tag.equals("inside")) {
                world.setBlockState(pos, ModBlocks.MUSHROOM_INSIDE.getDefaultState(), 2 | 16);
            } else if (tag.equals("origin")) {
                world.setBlockState(pos, this.stem, 2 | 16);
            }
        }
    }

    @Nullable
    private BlockPos computeCorrectedOrigin(BlockPos origin, Template template, PlacementSettings placementSettings) {
        Map<BlockPos, String> dataBlocks = template.getDataBlocks(BlockPos.ORIGIN, placementSettings);
        BlockPos localOrigin = this.reverseLookup(dataBlocks, "origin");
        if (localOrigin == null) {
            return null;
        }
        return origin.subtract(localOrigin);
    }

    private PlacementSettings buildPlacementSettings(Random rand) {
        Rotation[] rotations = Rotation.values();
        Mirror[] mirrors = Mirror.values();

        return new PlacementSettings()
                .setRotation(rotations[rand.nextInt(rotations.length)])
                .setMirror(mirrors[rand.nextInt(mirrors.length)]);
    }

    @Nullable
    private BlockPos reverseLookup(Map<BlockPos, String> data, String value) {
        for (Map.Entry<BlockPos, String> entry : data.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM;
    }

    private enum FungiShape {
        FLAT(new String[] { "mushroom_stem_flat_1", "mushroom_stem_flat_2" }, new String[] { "mushroom_hat_flat_1" }),
        SLANT(new String[] { "mushroom_stem_slant_1", "mushroom_stem_slant_2" }, new String[] { "mushroom_hat_slant_1" });

        private final String[] stemTemplates;
        private final String[] hatTemplates;

        FungiShape(String[] stemTemplates, String[] hatTemplates) {
            this.stemTemplates = stemTemplates;
            this.hatTemplates = hatTemplates;
        }

        public ResourceLocation getStemTemplate(Random random) {
            String template = this.stemTemplates[random.nextInt(this.stemTemplates.length)];
            return new ResourceLocation(Midnight.MODID, "mushroom/" + template);
        }

        public ResourceLocation getHatTemplate(Random random) {
            String template = this.hatTemplates[random.nextInt(this.hatTemplates.length)];
            return new ResourceLocation(Midnight.MODID, "mushroom/" + template);
        }
    }
}
