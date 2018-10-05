package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MidnightWorldProvider extends WorldProvider {
    private static final Vec3d FOG_COLOR = new Vec3d(0.075, 0.03, 0.1125);

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new MidnightBiomeProvider(this.world.getWorldInfo());
        this.nether = true;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MidnightChunkGenerator(this.world);
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MIDNIGHT;
    }

    @Override
    public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
        return WorldSleepResult.BED_EXPLODES;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean shouldClientCheckLighting() {
        return false;
    }

    @Override
    public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
        return true;
    }

    @Override
    protected void generateLightBrightnessTable() {
        float baseLight = 0.08F;
        for (int i = 0; i <= 15; ++i) {
            float alpha = 1.0F - i / 15.0F;
            float brightness = (1.0F - alpha) / (alpha * 10.0F + 1.0F);
            this.lightBrightnessTable[i] = (float) (Math.pow(brightness, 2.5F) * 3.0F) + baseLight;
        }
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        colors[0] = blockLight * 0.93F + 0.07F;
        colors[1] = blockLight * 0.96F + 0.03F;
        colors[2] = blockLight * 0.94F + 0.16F;
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    public boolean hasSkyLight() {
        return false;
    }

    @Override
    public float getSunBrightnessFactor(float partialTicks) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float partialTicks) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return FOG_COLOR;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    @Override
    public void calculateInitialWeather() {
    }

    @Override
    public void updateWeather() {
    }
}
