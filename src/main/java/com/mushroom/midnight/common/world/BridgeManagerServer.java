package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.network.BridgeRemovalMessage;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class BridgeManagerServer extends WorldSavedData implements BridgeManager {
    public static final String KEY = Midnight.MODID + ".bridge_manager";

    private final Int2ObjectMap<RiftBridge> bridges = new Int2ObjectOpenHashMap<>();
    private int currentId;

    public BridgeManagerServer(String key) {
        super(key);
    }

    public static BridgeManagerServer get(MinecraftServer server) {
        DimensionSavedDataManager storage = server.getWorld(DimensionType.field_223227_a_).getSavedData();
        return storage.getOrCreate(() -> new BridgeManagerServer(KEY), KEY);
    }

    @Override
    public void update() {
        ObjectIterator<RiftBridge> iterator = this.bridges.values().iterator();
        while (iterator.hasNext()) {
            RiftBridge bridge = iterator.next();
            bridge.tickTimers();

            if (bridge.tickState()) {
                bridge.exists = false;
                this.notifyRemoval(bridge);

                iterator.remove();
            }
        }
    }

    @Override
    public RiftBridge createBridge(RiftAttachment attachment) {
        return new RiftBridge(this.currentId++, attachment);
    }

    @Override
    public void addBridge(RiftBridge bridge) {
        this.bridges.put(bridge.getId(), bridge);
        this.markDirty();
    }

    @Override
    public void removeBridge(int id) {
        RiftBridge bridge = this.bridges.remove(id);
        if (bridge != null) {
            this.notifyRemoval(bridge);
        }
    }

    private void notifyRemoval(RiftBridge bridge) {
        bridge.exists = false;
        bridge.getTracker().sendToTracking(new BridgeRemovalMessage(bridge.getId()));

        this.markDirty();
    }

    @Nullable
    @Override
    public RiftBridge getBridge(int id) {
        return this.bridges.get(id);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("current_id", this.currentId);

        ListNBT bridgeList = new ListNBT();
        for (RiftBridge bridge : this.bridges.values()) {
            bridgeList.add(bridge.serialize(new CompoundNBT()));
        }
        compound.put("bridges", bridgeList);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        this.currentId = compound.getInt("current_id");

        ListNBT bridgeList = compound.getList("bridges", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < bridgeList.size(); i++) {
            CompoundNBT bridgeCompound = bridgeList.getCompound(i);
            RiftBridge bridge = RiftBridge.deserialize(bridgeCompound);
            this.bridges.put(bridge.getId(), bridge);
        }
    }
}
