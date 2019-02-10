package com.mushroom.midnight.common;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.MidnightBiomeLayer;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.capability.RiftTravelCooldown;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.event.RifterCaptureEvent;
import com.mushroom.midnight.common.event.RifterReleaseEvent;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModEffects;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import com.mushroom.midnight.common.world.MidnightWorldEntitySpawner;
import com.mushroom.midnight.common.world.RiftSpawnHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class CommonEventHandler {
    public static final float SOUND_TRAVEL_DISTANCE_MULTIPLIER = 2.0F;
    private static final ThreadLocal<DimensionType> TICKING_DIMENSION = ThreadLocal.withInitial(() -> null);

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Midnight.MODID, "rift_cooldown"), new RiftTravelCooldown());
        if (event.getObject() instanceof EntityLivingBase) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "rifter_captured"), new RifterCapturable());
        }
    }

    @SubscribeEvent
    public static void onAttachChunkCapabilities(AttachCapabilitiesEvent<Chunk> event) {
        if (Helper.isMidnightDimension(event.getObject().getWorld())) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "cavernous_biomes"), new CavernousBiomeStore());
        }
    }

    @SubscribeEvent
    public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        World world = event.getObject();

        if (Helper.isMidnightDimension(world)) {
            MultiLayerBiomeSampler sampler = new MultiLayerBiomeSampler();

            GenLayer surfaceProcedure = MidnightBiomeLayer.SURFACE.buildProcedure();
            GenLayer undergroundProcedure = MidnightBiomeLayer.UNDERGROUND.buildProcedure();

            sampler.put(MidnightBiomeLayer.SURFACE, BiomeLayerSampler.fromGenLayer(world, surfaceProcedure, Biome::getBiomeForId));
            sampler.put(MidnightBiomeLayer.UNDERGROUND, BiomeLayerSampler.fromGenLayer(world, undergroundProcedure, ModCavernousBiomes::fromId));

            event.addCapability(new ResourceLocation(Midnight.MODID, "biome_sampler"), sampler);
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        GameRules gameRules = event.getWorld().getGameRules();
        if (!gameRules.hasRule("doRiftSpawning")) {
            gameRules.addGameRule("doRiftSpawning", "true", GameRules.ValueType.BOOLEAN_VALUE);
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        RiftTravelCooldown cooldownCap = entity.getCapability(Midnight.RIFT_TRAVEL_COOLDOWN_CAP, null);
        if (cooldownCap != null) {
            cooldownCap.update(entity);
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.world.isRemote) {
                GlobalBridgeManager.getServer().update();
                if (Helper.isMidnightDimension(event.world) && event.world.getGameRules().getBoolean("doMobSpawning") && event.world.getWorldInfo().getTerrainType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
                    MidnightWorldEntitySpawner.findChunksForSpawning((WorldServer) event.world);
                }
            }
            TICKING_DIMENSION.set(event.world.provider.getDimensionType());
        } else {
            TICKING_DIMENSION.set(null);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (MidnightConfig.general.riftSpawnRarity > 0 && event.phase == TickEvent.Phase.END) {
            RiftSpawnHandler.update();
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity trueSource = source.getTrueSource();
        if (trueSource instanceof EntityLivingBase && ((EntityLivingBase) trueSource).isPotionActive(ModEffects.STUNNED)) {
            event.setAmount(0.0F);
        }
    }

    @SubscribeEvent
    public static void onRifterCapture(RifterCaptureEvent event) {
        EntityLivingBase captured = event.getCaptured();
        if (captured instanceof EntityPlayer) {
            ((EntityPlayer) captured).eyeHeight = captured.width / 2.0F;
        }
    }

    @SubscribeEvent
    public static void onRifterRelease(RifterReleaseEvent event) {
        EntityLivingBase captured = event.getCaptured();
        if (captured instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) captured;
            player.eyeHeight = player.getDefaultEyeHeight();
        }
    }

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        if (event.getResultStatus() == null) {
            EntityPlayer player = event.getEntityPlayer();
            BlockPos bedPos = event.getPos();

            List<EntityRift> rifts = player.world.getEntitiesWithinAABB(EntityRift.class, new AxisAlignedBB(bedPos).grow(6.0));
            if (!rifts.isEmpty()) {
                event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
                player.sendStatusMessage(new TextComponentTranslation("status.midnight.rift_nearby"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (TICKING_DIMENSION.get() == ModDimensions.MIDNIGHT) {
            event.setVolume(event.getVolume() * SOUND_TRAVEL_DISTANCE_MULTIPLIER);
        }
    }
}
