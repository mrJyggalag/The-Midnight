package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.registry.MidnightLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PenumbrianEntity extends MonsterEntity {
    public PenumbrianEntity(EntityType<? extends PenumbrianEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return false;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return MidnightLootTables.LOOT_TABLE_PENUMBRIAN;
    }
}
