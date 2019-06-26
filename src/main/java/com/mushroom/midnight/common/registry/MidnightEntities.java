package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.CloudEntity;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.projectile.SporeBombEntity;
import com.mushroom.midnight.common.entity.projectile.ThrownGeodeEntity;
import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import com.mushroom.midnight.common.entity.creature.DeceitfulSnapperEntity;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.entity.creature.NovaEntity;
import com.mushroom.midnight.common.entity.creature.PenumbrianEntity;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.entity.creature.TreeHopperEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightEntities {
    private static int currentEntityId;

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                EntityEntryBuilder.create()
                        .entity(RiftEntity.class)
                        .factory(RiftEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "rift"), currentEntityId++)
                        .name(Midnight.MODID + ".rift")
                        .tracker(1024, 20, false)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(RifterEntity.class)
                        .factory(RifterEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "rifter"), currentEntityId++)
                        .name(Midnight.MODID + ".rifter")
                        .tracker(80, 3, true)
                        .egg(0x384740, 0x5E8C6C)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(HunterEntity.class)
                        .factory(HunterEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "hunter"), currentEntityId++)
                        .name(Midnight.MODID + ".hunter")
                        .tracker(80, 2, true)
                        .egg(0x2C3964, 0x19203A)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(BladeshroomCapEntity.class)
                        .factory(BladeshroomCapEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "bladeshroom_cap"), currentEntityId++)
                        .name(Midnight.MODID + ".bladeshroom_cap")
                        .tracker(64, 1, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(NovaEntity.class)
                        .factory(NovaEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "nova"), currentEntityId++)
                        .name(Midnight.MODID + ".nova")
                        .tracker(80, 2, true)
                        .egg(0x932C3B, 0x47415E)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(CrystalBugEntity.class)
                        .factory(CrystalBugEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "crystal_bug"), currentEntityId++)
                        .name(Midnight.MODID + ".crystal_bug")
                        .tracker(80, 3, false)
                        .egg(0x4B2277, 0xAF27E0)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(PenumbrianEntity.class)
                        .factory(PenumbrianEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "penumbrian"), currentEntityId++)
                        .name(Midnight.MODID + ".penumbrian")
                        .tracker(80, 3, true)
                        //.egg(0x8CDCC9, 0x456B6F)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(TreeHopperEntity.class)
                        .factory(TreeHopperEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "tree_hopper"), currentEntityId++)
                        .name(Midnight.MODID + ".tree_hopper")
                        .tracker(80, 3, true)
                        //.egg(0x6E5A84, 0x303847)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(StingerEntity.class)
                        .factory(StingerEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "stinger"), currentEntityId++)
                        .name(Midnight.MODID + ".stinger")
                        .tracker(80, 3, true)
                        .egg(0x291E23, 0x4D6A43)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(NightStagEntity.class)
                        .factory(NightStagEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "nightstag"), currentEntityId++)
                        .name(Midnight.MODID + ".nightstag")
                        .tracker(80, 3, true)
                        .egg(0x946CC2, 0x221F1D)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(DeceitfulSnapperEntity.class)
                        .factory(DeceitfulSnapperEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "deceitful_snapper"), currentEntityId++)
                        .name(Midnight.MODID + ".deceitful_snapper")
                        .tracker(80, 3, true)
                        .egg(0x45404E, 0x906F99)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(SkulkEntity.class)
                        .factory(SkulkEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "skulk"), currentEntityId++)
                        .name(Midnight.MODID + ".skulk")
                        .tracker(80, 3, true)
                        .egg(0x4B5065, 0x4E268A)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(ThrownGeodeEntity.class)
                        .factory(ThrownGeodeEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "thrown_geode"), currentEntityId++)
                        .name(Midnight.MODID + ".thrown_geode")
                        .tracker(64, 10, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(SporeBombEntity.class)
                        .factory(SporeBombEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "spore_bomb"), currentEntityId++)
                        .name(Midnight.MODID + ".spore_bomb")
                        .tracker(64, 10, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(CloudEntity.class)
                        .factory(CloudEntity::new)
                        .id(new ResourceLocation(Midnight.MODID, "cloud"), currentEntityId++)
                        .name(Midnight.MODID + ".cloud")
                        .tracker(160, Integer.MAX_VALUE, true)
                        .build()
        );

        EntitySpawnPlacementRegistry.setPlacementType(HunterEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(CrystalBugEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(NovaEntity.class, MobEntity.SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(DeceitfulSnapperEntity.class, MobEntity.SpawnPlacementType.IN_WATER);
    }
}
