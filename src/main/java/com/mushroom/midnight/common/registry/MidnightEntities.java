package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.entity.CloudEntity;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.entity.creature.NovaEntity;
import com.mushroom.midnight.common.entity.creature.PenumbrianEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.entity.creature.TreeHopperEntity;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.entity.projectile.SporeBombEntity;
import com.mushroom.midnight.common.entity.projectile.ThrownGeodeEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(MODID)
public class MidnightEntities {
    public static final EntityType<RiftEntity> rift = EntityType.Builder.<RiftEntity>create(RiftEntity::new, EntityClassification.MISC)
            .setTrackingRange(1024)
            .setUpdateInterval(20)
            .setShouldReceiveVelocityUpdates(false)
            .size(1.8f, 4.0f)
            .setCustomClientFactory(RiftEntity::new)
            .immuneToFire()
            .build(MODID + ":rift");
    public static final EntityType<RifterEntity> rifter = EntityType.Builder.create(RifterEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 1.8f) // .egg(0x384740, 0x5E8C6C)
            .build(MODID + ":rifter");
    public static final EntityType<HunterEntity> hunter = EntityType.Builder.create(HunterEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(2)
            .setShouldReceiveVelocityUpdates(true)
            .size(1f, 1f) // .egg(0x2C3964, 0x19203A)
            .build(MODID + ":hunter");
    public static final EntityType<BladeshroomCapEntity> bladeshroom_cap = EntityType.Builder.<BladeshroomCapEntity>create(BladeshroomCapEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(MODID + ":bladeshroom_cap");
    public static final EntityType<NovaEntity> nova = EntityType.Builder.create(NovaEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(2)
            .setShouldReceiveVelocityUpdates(true)
            .size(1.2f, 1.8f) // .egg(0x932C3B, 0x47415E)
            .immuneToFire()
            .build(MODID + ":nova");
    public static final EntityType<CrystalBugEntity> crystal_bug = EntityType.Builder.create(CrystalBugEntity::new, MIDNIGHT_AMBIENT)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(false)
            .size(0.2f, 0.2f) // .egg(0x4B2277, 0xAF27E0)
            .build(MODID + ":crystal_bug");
    public static final EntityType<PenumbrianEntity> penumbrian = EntityType.Builder.create(PenumbrianEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.5f, 0.5f) // .egg(0x8CDCC9, 0x456B6F)
            .build(MODID + ":penumbrian");
    public static final EntityType<TreeHopperEntity> tree_hopper = EntityType.Builder.create(TreeHopperEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.5f, 0.5f) // .egg(0x6E5A84, 0x303847)
            .build(MODID + ":tree_hopper");
    public static final EntityType<StingerEntity> stinger = EntityType.Builder.create(StingerEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.2f, 0.2f) // .egg(0x291E23, 0x4D6A43)
            .build(MODID + ":stinger");
    public static final EntityType<NightStagEntity> nightstag = EntityType.Builder.create(NightStagEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.9f, 1.87f) // .egg(0x946CC2, 0x221F1D)
            .build(MODID + ":nightstag");
    public static final EntityType<DeceitfulSnapperEntity> deceitful_snapper = EntityType.Builder.create(DeceitfulSnapperEntity::new, MIDNIGHT_MOB)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.4f, 0.4f) // .egg(0x45404E, 0x906F99)
            .build(MODID + ":deceitful_snapper");
    public static final EntityType<SkulkEntity> skulk = EntityType.Builder.create(SkulkEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6f, 0.6f) // .egg(0x4B5065, 0x4E268A)
            .build(MODID + ":skulk");
    public static final EntityType<ThrownGeodeEntity> thrown_geode = EntityType.Builder.<ThrownGeodeEntity>create(ThrownGeodeEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build(MODID + ":thrown_geode");
    public static final EntityType<SporeBombEntity> spore_bomb = EntityType.Builder.<SporeBombEntity>create(SporeBombEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build(MODID + ":spore_bomb");
    public static final EntityType<CloudEntity> cloud = EntityType.Builder.<CloudEntity>create(CloudEntity::new, EntityClassification.MISC)
            .setTrackingRange(160)
            .setUpdateInterval(Integer.MAX_VALUE)
            .setShouldReceiveVelocityUpdates(true)
            .immuneToFire()
            .build(MODID + ":cloud");

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        rift.setRegistryName(MODID, "rift");
        event.getRegistry().register(rift);
        rifter.setRegistryName(MODID, "rifter");
        event.getRegistry().register(rifter);
        hunter.setRegistryName(MODID, "hunter");
        event.getRegistry().register(hunter);
        bladeshroom_cap.setRegistryName(MODID, "bladeshroom_cap");
        event.getRegistry().register(bladeshroom_cap);
        nova.setRegistryName(MODID, "nova");
        event.getRegistry().register(nova);
        crystal_bug.setRegistryName(MODID, "crystal_bug");
        event.getRegistry().register(crystal_bug);
        penumbrian.setRegistryName(MODID, "penumbrian");
        event.getRegistry().register(penumbrian);
        tree_hopper.setRegistryName(MODID, "tree_hopper");
        event.getRegistry().register(tree_hopper);
        stinger.setRegistryName(MODID, "stinger");
        event.getRegistry().register(stinger);
        nightstag.setRegistryName(MODID, "nightstag");
        event.getRegistry().register(nightstag);
        deceitful_snapper.setRegistryName(MODID, "deceitful_snapper");
        event.getRegistry().register(deceitful_snapper);
        skulk.setRegistryName(MODID, "skulk");
        event.getRegistry().register(skulk);
        thrown_geode.setRegistryName(MODID, "thrown_geode");
        event.getRegistry().register(thrown_geode);
        spore_bomb.setRegistryName(MODID, "spore_bomb");
        event.getRegistry().register(spore_bomb);
        cloud.setRegistryName(MODID, "cloud");
        event.getRegistry().register(cloud);

        // TODO check placement logic for spawn
        //EntitySpawnPlacementRegistry.setPlacementType(HunterEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        //EntitySpawnPlacementRegistry.setPlacementType(CrystalBugEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        //EntitySpawnPlacementRegistry.setPlacementType(NovaEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        //EntitySpawnPlacementRegistry.setPlacementType(DeceitfulSnapperEntity.class, MobEntity.SpawnPlacementType.IN_WATER);
    }
}
