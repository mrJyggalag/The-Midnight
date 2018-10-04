package com.mushroom.midnight.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class DelegatedStorage<N extends NBTBase, T extends ICapabilitySerializable<N>> implements Capability.IStorage<T> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
        instance.deserializeNBT((N) nbt);
    }
}
