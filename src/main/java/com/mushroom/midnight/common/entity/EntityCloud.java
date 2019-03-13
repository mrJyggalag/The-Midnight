package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.client.particle.MidnightParticles;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EntityCloud extends Entity {
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(EntityCloud.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityCloud.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.createKey(EntityCloud.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> PARTICLE = EntityDataManager.createKey(EntityCloud.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PARTICLE_PARAM = EntityDataManager.createKey(EntityCloud.class, DataSerializers.VARINT);
    private PotionType potion = PotionTypes.EMPTY;
    private final List<PotionEffect> effects = new ArrayList<>();
    private final Map<Entity, Integer> reapplicationDelayMap = new HashMap<>();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private boolean colorSet;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    private EntityLivingBase owner;
    private UUID ownerUniqueId;
    private boolean allowTeleport = false;

    public EntityCloud(World world) {
        super(world);
        this.noClip = true;
        this.isImmuneToFire = true;
        setRadius(3f);
    }

    public EntityCloud(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public EntityCloud setAllowTeleport() {
        this.allowTeleport = true;
        return this;
    }

    protected void entityInit() {
        this.dataManager.register(COLOR, 0);
        this.dataManager.register(RADIUS, 0.5f);
        this.dataManager.register(IGNORE_RADIUS, Boolean.FALSE);
        this.dataManager.register(PARTICLE, MidnightParticles.AMBIENT_SPORE.ordinal());
        this.dataManager.register(PARTICLE_PARAM, 0);
    }

    public EntityCloud setRadius(float radius) {
        double x = this.posX;
        double y = this.posY;
        double z = this.posZ;
        setSize(radius * 2f, 0.5f);
        setPosition(x, y, z);
        if (!this.world.isRemote) {
            this.dataManager.set(RADIUS, radius);
        }
        return this;
    }

    public float getRadius() {
        return this.dataManager.get(RADIUS);
    }

    public EntityCloud setPotion(PotionType potion) {
        this.potion = potion;
        if (!this.colorSet) {
            updateFixedColor();
        }
        return this;
    }

    private void updateFixedColor() {
        if (this.potion == PotionTypes.EMPTY && this.effects.isEmpty()) {
            this.dataManager.set(COLOR, 0);
        } else {
            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.effects)));
        }
    }

    public EntityCloud addEffect(PotionEffect effect) {
        this.effects.add(effect);
        if (!this.colorSet) {
            updateFixedColor();
        }
        return this;
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    public EntityCloud setColor(int color) {
        this.colorSet = true;
        this.dataManager.set(COLOR, color);
        return this;
    }

    public MidnightParticles getParticle() {
        return MidnightParticles.fromId(this.dataManager.get(PARTICLE));
    }

    public EntityCloud setParticle(MidnightParticles particle) {
        this.dataManager.set(PARTICLE, particle.ordinal());
        return this;
    }

    public int getParticleParam() {
        return this.dataManager.get(PARTICLE_PARAM);
    }

    public EntityCloud setParticleParam(int param) {
        this.dataManager.set(PARTICLE_PARAM, param);
        return this;
    }

    protected void setIgnoreRadius(boolean ignoreRadius) {
        this.dataManager.set(IGNORE_RADIUS, ignoreRadius);
    }

    public boolean shouldIgnoreRadius() {
        return this.dataManager.get(IGNORE_RADIUS);
    }

    public int getDuration() {
        return this.duration;
    }

    public EntityCloud setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public void onUpdate() {
        super.onUpdate();
        boolean flag = shouldIgnoreRadius();
        float f = getRadius();
        if (this.world.isRemote) {
            MidnightParticles particle = getParticle();
            if (flag) {
                if (this.rand.nextBoolean()) {
                    for (int i = 0; i < 2; ++i) {
                        float f1 = this.rand.nextFloat() * ((float) Math.PI * 2f);
                        float f2 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2f;
                        float f3 = MathHelper.cos(f1) * f2;
                        float f4 = MathHelper.sin(f1) * f2;
                        particle.spawn(this.world, this.posX + (double) f3, this.posY, this.posZ + (double) f4, 0d, 0d, 0d, getParticleParam());
                    }
                }
            } else {
                float f5 = (float) Math.PI * f * f;
                for (int k1 = 0; (float) k1 < f5; ++k1) {
                    float f6 = this.rand.nextFloat() * ((float) Math.PI * 2f);
                    float f7 = MathHelper.sqrt(this.rand.nextFloat()) * f;
                    float f8 = MathHelper.cos(f6) * f7;
                    float f9 = MathHelper.sin(f6) * f7;
                    particle.spawn(this.world, this.posX + (double) f8, this.posY, this.posZ + (double) f9, (0.5d - this.rand.nextDouble()) * 0.15d, 0.009999999776482582d, (0.5d - this.rand.nextDouble()) * 0.15d, getParticleParam());
                }
            }
        } else {
            if (this.ticksExisted >= this.waitTime + this.duration) {
                setDead();
                return;
            }
            boolean flag1 = this.ticksExisted < this.waitTime;
            if (flag != flag1) {
                setIgnoreRadius(flag1);
            }
            if (flag1) {
                return;
            }
            if (this.radiusPerTick != 0f) {
                f += this.radiusPerTick;
                if (f < 0.5f) {
                    setDead();
                    return;
                }
                setRadius(f);
            }
            if (this.ticksExisted % 5 == 0) {
                this.reapplicationDelayMap.entrySet().removeIf(entry -> this.ticksExisted >= entry.getValue());
                List<PotionEffect> potions = new ArrayList<>();
                for (PotionEffect effect : this.potion.getEffects()) {
                    potions.add(new PotionEffect(effect.getPotion(), effect.getDuration() / 4, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                }
                potions.addAll(this.effects);
                if (potions.isEmpty()) {
                    this.reapplicationDelayMap.clear();
                } else {
                    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox());
                    if (!list.isEmpty()) {
                        for (EntityLivingBase entity : list) {
                            if (!this.reapplicationDelayMap.containsKey(entity) && entity.canBeHitWithPotion()) {
                                double d0 = entity.posX - this.posX;
                                double d1 = entity.posZ - this.posZ;
                                double d2 = d0 * d0 + d1 * d1;
                                if (d2 <= (double) (f * f)) {
                                    this.reapplicationDelayMap.put(entity, this.ticksExisted + this.reapplicationDelay);
                                    for (PotionEffect effect : potions) {
                                        if (effect.getPotion().isInstant()) {
                                            effect.getPotion().affectEntity(this, getOwner(), entity, effect.getAmplifier(), 0.5d);
                                        } else {
                                            entity.addPotionEffect(new PotionEffect(effect));
                                        }
                                    }
                                    if (this.radiusOnUse != 0f) {
                                        f += this.radiusOnUse;
                                        if (f < 0.5f) {
                                            setDead();
                                            return;
                                        }
                                        setRadius(f);
                                    }
                                    if (this.durationOnUse != 0) {
                                        this.duration += this.durationOnUse;
                                        if (this.duration <= 0) {
                                            setDead();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // TODO could be a move
        if (this.allowTeleport && !this.world.isRemote && isEntityAlive() && this.world.getTotalWorldTime() % 40 == 0) {
            List<Entity> list = this.world.getEntitiesInAABBexcluding(getOwner(), getEntityBoundingBox().grow(5d), p -> p instanceof EntityLivingBase && p.isEntityAlive());
            if (!list.isEmpty()) {
                Entity entity = list.get(this.world.rand.nextInt(list.size()));
                setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
            }
        }
    }

    public EntityCloud setRadiusOnUse(float radiusOnUseIn) {
        this.radiusOnUse = radiusOnUseIn;
        return this;
    }

    public EntityCloud setRadiusPerTick(float radiusPerTickIn) {
        this.radiusPerTick = radiusPerTickIn;
        return this;
    }

    public EntityCloud setWaitTime(int waitTimeIn) {
        this.waitTime = waitTimeIn;
        return this;
    }

    public EntityCloud setOwner(@Nullable EntityLivingBase owner) {
        this.owner = owner;
        this.ownerUniqueId = owner == null ? null : owner.getUniqueID();
        return this;
    }

    @Nullable
    public EntityLivingBase getOwner() {
        if (this.owner == null && this.ownerUniqueId != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer) this.world).getEntityFromUuid(this.ownerUniqueId);
            if (entity instanceof EntityLivingBase) {
                this.owner = (EntityLivingBase) entity;
            }
        }
        return this.owner;
    }

    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.ticksExisted = compound.getInteger("Age");
        this.duration = compound.getInteger("Duration");
        this.waitTime = compound.getInteger("WaitTime");
        this.reapplicationDelay = compound.getInteger("ReapplicationDelay");
        this.durationOnUse = compound.getInteger("DurationOnUse");
        this.radiusOnUse = compound.getFloat("RadiusOnUse");
        this.radiusPerTick = compound.getFloat("RadiusPerTick");
        setRadius(compound.getFloat("Radius"));
        this.ownerUniqueId = compound.getUniqueId("OwnerUUID");
        if (compound.hasKey("particle_id", Constants.NBT.TAG_INT)) {
            setParticle(MidnightParticles.fromId(compound.getInteger("particle_id")));
        }
        if (compound.hasKey("particle_param", Constants.NBT.TAG_INT)) {
            setParticleParam(compound.getInteger("particle_param"));
        }
        if (compound.hasKey("Color", 99)) {
            setColor(compound.getInteger("Color"));
        }
        if (compound.hasKey("Potion", 8)) {
            setPotion(PotionUtils.getPotionTypeFromNBT(compound));
        }
        if (compound.hasKey("Effects", 9)) {
            NBTTagList nbttaglist = compound.getTagList("Effects", 10);
            this.effects.clear();
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT(nbttaglist.getCompoundTagAt(i));
                if (effect != null) {
                    addEffect(effect);
                }
            }
        }
    }

    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("Age", this.ticksExisted);
        compound.setInteger("Duration", this.duration);
        compound.setInteger("WaitTime", this.waitTime);
        compound.setInteger("ReapplicationDelay", this.reapplicationDelay);
        compound.setInteger("DurationOnUse", this.durationOnUse);
        compound.setFloat("RadiusOnUse", this.radiusOnUse);
        compound.setFloat("RadiusPerTick", this.radiusPerTick);
        compound.setFloat("Radius", getRadius());
        compound.setInteger("particle_id", getParticle().ordinal());
        compound.setInteger("particle_param", getParticleParam());
        if (this.ownerUniqueId != null) {
            compound.setUniqueId("OwnerUUID", this.ownerUniqueId);
        }
        if (this.colorSet) {
            compound.setInteger("Color", getColor());
        }
        if (this.potion != PotionTypes.EMPTY && this.potion != null) {
            compound.setString("Potion", PotionType.REGISTRY.getNameForObject(this.potion).toString());
        }
        if (!this.effects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();
            for (PotionEffect effect : this.effects) {
                nbttaglist.appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            compound.setTag("Effects", nbttaglist);
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (RADIUS.equals(key)) {
            setRadius(getRadius());
        }
        super.notifyDataManagerChange(key);
    }

    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }
}
