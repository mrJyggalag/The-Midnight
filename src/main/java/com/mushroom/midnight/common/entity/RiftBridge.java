package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.util.RiftEntityReference;
import com.mushroom.midnight.common.entity.util.ToggleAnimation;
import com.mushroom.midnight.common.network.MessageBridgeState;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.util.BitFlags;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.Collection;

public class RiftBridge {
    public final ToggleAnimation open = new ToggleAnimation(EntityRift.OPEN_TIME);
    public final ToggleAnimation unstable = new ToggleAnimation(EntityRift.UNSTABLE_TIME);
    public boolean used;

    public boolean exists = true;

    private final int bridgeId;
    private RiftAttachment attachment;

    private boolean sentOpen;
    private boolean sentUnstable;
    private boolean sentUsed;

    private int ticks;

    private int prevOpenTimer;
    private int prevUnstableTimer;

    private final RiftEntityReference source = new RiftEntityReference();
    private final RiftEntityReference target = new RiftEntityReference();

    public RiftBridge(int bridgeId, RiftAttachment attachment) {
        this.bridgeId = bridgeId;
        this.attachment = attachment;

        this.open.set(true);
    }

    public void tickTimers() {
        this.prevOpenTimer = this.open.getTimer();
        this.prevUnstableTimer = this.unstable.getTimer();

        this.ticks++;

        if (this.open.get()) {
            this.open.setSpeed(1);
        } else {
            this.open.setSpeed(EntityRift.CLOSE_SPEED);
        }

        this.open.update();
        this.unstable.update();
    }

    public boolean tickState() {
        this.trySpawnEndpoint(DimensionType.OVERWORLD);
        this.trySpawnEndpoint(ModDimensions.MIDNIGHT);

        if (this.unstable.get()) {
            if (this.unstable.getTimer() >= EntityRift.UNSTABLE_TIME && this.open.get()) {
                this.open.set(false);
            }
        } else if (this.ticks > EntityRift.LIFETIME) {
            this.unstable.set(true);
        }

        if (this.isDirty()) {
            this.broadcastState();
        }

        return !this.open.get() && this.open.getTimer() <= 0;
    }

    private void trySpawnEndpoint(DimensionType endpointDimension) {
        RiftEntityReference endpointReference = this.getEndpointReference(endpointDimension);
        if (!endpointReference.hasReference()) {
            World world = DimensionManager.getWorld(endpointDimension.getId());
            if (world != null && world.isBlockLoaded(this.attachment.getPos())) {
                this.spawnRiftEntity(world);
            }
        }
    }

    private void spawnRiftEntity(World world) {
        EntityRift rift = new EntityRift(world);
        rift.acceptBridge(this);
        this.attachment.apply(rift);

        world.spawnEntity(rift);
    }

    public EntityRift computeEndpoint(DimensionType endpointDimension) {
        RiftEntityReference endpointReference = this.getEndpointReference(endpointDimension);
        if (!endpointReference.hasReference()) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            this.spawnRiftEntity(server.getWorld(endpointDimension.getId()));
        }

        return endpointReference.compute();
    }

    public void broadcastState() {
        this.sendToTracking(new MessageBridgeState(this));
        this.clearDirt();
    }

    public void writeState(ByteBuf buffer) {
        buffer.writeByte(new BitFlags()
                .withBit(0, this.open.get())
                .withBit(1, this.unstable.get())
                .withBit(2, this.used)
                .toInner()
        );

        buffer.writeInt((this.ticks & 0xFFFF) << 16
                | (this.open.getTimer() & 0xFF) << 8
                | this.unstable.getTimer() & 0xFF
        );
    }

    public void handleState(ByteBuf buffer) {
        BitFlags flags = new BitFlags(buffer.readByte());

        this.open.set(flags.getBit(0));
        this.unstable.set(flags.getBit(1));
        this.used = flags.getBit(2);

        long packedTimers = buffer.readUnsignedInt();

        this.ticks = (int) ((packedTimers >> 16) & 0xFFFF);
        this.open.setTimer((int) ((packedTimers >> 8) & 0xFF));
        this.unstable.setTimer((int) (packedTimers & 0xFF));
    }

    public void clearDirt() {
        this.sentOpen = this.open.get();
        this.sentUnstable = this.unstable.get();
        this.sentUsed = this.used;
    }

    public boolean isDirty() {
        return this.sentOpen != this.open.get()
                || this.sentUnstable != this.unstable.get()
                || this.sentUsed != this.used;
    }

    public int getBridgeId() {
        return this.bridgeId;
    }

    public void setAttachment(RiftAttachment attachment) {
        this.attachment = attachment;
    }

    public RiftAttachment getAttachment() {
        return this.attachment;
    }

    public float getOpenAnimation(float partialTicks) {
        return this.prevOpenTimer + (this.open.getTimer() - this.prevOpenTimer) * partialTicks;
    }

    public float getUnstableAnimation(float partialTicks) {
        return this.prevUnstableTimer + (this.unstable.getTimer() - this.prevUnstableTimer) * partialTicks;
    }

    public int getTimeUntilClose() {
        if (this.unstable.get()) {
            return 0;
        }
        return Math.max(EntityRift.LIFETIME - this.ticks, 0);
    }

    public void sendToTracking(IMessage message) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        Collection<EntityPlayer> trackingPlayers = this.getTrackingPlayers(server);
        for (EntityPlayer player : trackingPlayers) {
            if (player instanceof EntityPlayerMP) {
                Midnight.NETWORK.sendTo(message, (EntityPlayerMP) player);
            }
        }
    }

    private Collection<EntityPlayer> getTrackingPlayers(MinecraftServer server) {
        Collection<EntityPlayer> trackingPlayers = new ArrayList<>();

        EntityRift source = this.source.get();
        if (source != null) {
            WorldServer world = server.getWorld(source.dimension);
            trackingPlayers.addAll(world.getEntityTracker().getTrackingPlayers(source));
        }

        EntityRift target = this.target.get();
        if (target != null) {
            WorldServer world = server.getWorld(target.dimension);
            trackingPlayers.addAll(world.getEntityTracker().getTrackingPlayers(target));
        }

        return trackingPlayers;
    }

    public void accept(EntityRift rift) {
        DimensionType endpointDimension = rift.world.provider.getDimensionType();
        this.getEndpointReference(endpointDimension).set(rift);
    }

    private RiftEntityReference getEndpointReference(DimensionType endpointDimension) {
        return endpointDimension == ModDimensions.MIDNIGHT ? this.target : this.source;
    }

    public void close() {
        this.used = true;
        this.unstable.set(true);
    }
}
