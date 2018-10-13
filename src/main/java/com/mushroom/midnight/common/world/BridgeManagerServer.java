package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.RiftAttachment;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.network.MessageBridgeRemoval;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.MapStorage;
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
        MapStorage storage = server.getWorld(0).getMapStorage();
        BridgeManagerServer data = (BridgeManagerServer) storage.getOrLoadData(BridgeManagerServer.class, KEY);
        if (data == null) {
            data = new BridgeManagerServer(KEY);
            storage.setData(KEY, data);
        }
        return data;
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
        bridge.getTracker().sendToTracking(new MessageBridgeRemoval(bridge.getId()));

        this.markDirty();
    }

    @Nullable
    @Override
    public RiftBridge getBridge(int id) {
        return this.bridges.get(id);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("current_id", this.currentId);

        NBTTagList bridgeList = new NBTTagList();
        for (RiftBridge bridge : this.bridges.values()) {
            bridgeList.appendTag(bridge.serialize(new NBTTagCompound()));
        }
        compound.setTag("bridges", bridgeList);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.currentId = compound.getInteger("current_id");

        NBTTagList bridgeList = compound.getTagList("bridges", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < bridgeList.tagCount(); i++) {
            NBTTagCompound bridgeCompound = bridgeList.getCompoundTagAt(i);
            RiftBridge bridge = RiftBridge.deserialize(bridgeCompound);
            this.bridges.put(bridge.getId(), bridge);
        }
    }
}
