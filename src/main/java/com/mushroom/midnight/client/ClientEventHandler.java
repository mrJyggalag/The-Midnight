package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.client.sound.LoopingMidnightSound;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModEffects;
import com.mushroom.midnight.common.registry.ModSounds;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class ClientEventHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final ISound RUMBLE_SOUND = new LoopingMidnightSound(ModSounds.MIDNIGHT_RUMBLE, 1.0F);

    private static final float HOOK_SENSITIVITY = 0.2F;

    private static boolean sensitivityHooked;
    private static float lastSensitivity;

    private static long lastAmbientSoundTime;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!MC.isGamePaused()) {
            EntityPlayer player = MC.player;
            if (player == null) {
                return;
            }

            if (event.phase == TickEvent.Phase.END) {
                if (player.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
                    spawnAmbientParticles(player);
                    playAmbientSounds(player);
                } else {
                    pullSelfPlayer(player);
                }

                GameSettings settings = MC.gameSettings;
                if (player.isPotionActive(ModEffects.STUNNED)) {
                    if (!sensitivityHooked || settings.mouseSensitivity != HOOK_SENSITIVITY) {
                        lastSensitivity = settings.mouseSensitivity;
                    }
                    settings.mouseSensitivity = HOOK_SENSITIVITY;
                    sensitivityHooked = true;
                } else if (sensitivityHooked) {
                    settings.mouseSensitivity = lastSensitivity;
                    sensitivityHooked = false;
                }
            } else if (event.phase == TickEvent.Phase.START) {
                GlobalBridgeManager.getClient().update();
            }
        }
    }

    private static void pullSelfPlayer(EntityPlayer player) {
        AxisAlignedBB pullBounds = player.getEntityBoundingBox().grow(EntityRift.PULL_RADIUS);
        List<EntityRift> rifts = player.world.getEntitiesWithinAABB(EntityRift.class, pullBounds);
        for (EntityRift rift : rifts) {
            if (!rift.wasUsed()) {
                double pullIntensity = rift.getPullIntensity();
                rift.pullEntity(pullIntensity, player);
            }
        }
    }

    private static void playAmbientSounds(EntityPlayer player) {
        Random rand = player.world.rand;
        long worldTime = player.world.getTotalWorldTime();

        SoundHandler soundHandler = MC.getSoundHandler();
        if (!soundHandler.isSoundPlaying(RUMBLE_SOUND)) {
            soundHandler.playSound(RUMBLE_SOUND);
        }

        if (worldTime - lastAmbientSoundTime > 120 && rand.nextInt(100) == 0) {
            ResourceLocation ambientSound = ModSounds.MIDNIGHT_AMBIENT.getSoundName();

            float volume = rand.nextFloat() * 0.4F + 0.8F;
            float pitch = rand.nextFloat() * 0.6F + 0.7F;

            float x = (float) (player.posX + rand.nextFloat() - 0.5F);
            float y = (float) (player.posY + rand.nextFloat() - 0.5F);
            float z = (float) (player.posZ + rand.nextFloat() - 0.5F);

            ISound sound = new PositionedSoundRecord(ambientSound, SoundCategory.AMBIENT, volume, pitch, false, 0, ISound.AttenuationType.NONE, x, y, z);
            soundHandler.playSound(sound);

            lastAmbientSoundTime = worldTime;
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        Entity entity = event.getEntity();
        if (entity.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.015F);
        } else if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(ModEffects.STUNNED)) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.15F);
        }
    }

    private static void spawnAmbientParticles(EntityPlayer player) {
        Random random = player.world.rand;
        double originX = player.posX;
        double originY = player.posY;
        double originZ = player.posZ;
        for (int i = 0; i < 6; i++) {
            double particleX = originX + (random.nextInt(24) - random.nextInt(24));
            double particleY = originY + (random.nextInt(24) - random.nextInt(24));
            double particleZ = originZ + (random.nextInt(24) - random.nextInt(24));
            double velocityX = (random.nextDouble() - 0.5) * 0.04;
            double velocityY = (random.nextDouble() - 0.5) * 0.04;
            double velocityZ = (random.nextDouble() - 0.5) * 0.04;
            MidnightParticles.SPORE.spawn(player.world, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    public static void onApplyRotations(EntityLivingBase entity) {
        boolean captured = RifterCapturedCapability.isCaptured(entity);
        if (captured) {
            entity.limbSwing = 0.0F;
            entity.limbSwingAmount = entity.prevLimbSwingAmount = 0.0F;

            EntityUtil.Stance stance = EntityUtil.getStance(entity);
            if (stance == EntityUtil.Stance.QUADRUPEDAL) {
                GlStateManager.translate(0.0F, entity.height, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            } else {
                GlStateManager.translate(0.0F, entity.width / 2.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, entity.height / 2.0F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }
}
