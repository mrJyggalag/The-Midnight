package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TreeHopperEntity extends MonsterEntity {
    public TreeHopperEntity(World world) {
        super(world);
        setSize(0.5f, 0.5f);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        return false;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return MidnightLootTables.LOOT_TABLE_TREE_HOPPER;
    }
}
