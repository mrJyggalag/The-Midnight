package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.entities.EntityRift;
import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !MC.isGamePaused()) {
            EntityPlayer player = MC.player;
            if (player == null) {
                return;
            }
            if (player.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
                spawnAmbientParticles(player);
            } else {
                pullSelfPlayer(player);
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

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        Entity entity = event.getEntity();
        if (entity.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.02F);
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
}
