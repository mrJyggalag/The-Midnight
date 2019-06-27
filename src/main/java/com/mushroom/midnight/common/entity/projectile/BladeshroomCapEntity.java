package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class BladeshroomCapEntity extends ArrowEntity {
    private static final ThreadLocal<Boolean> HIT_ACTIVE = ThreadLocal.withInitial(() -> false);

    private static final float DAMAGE = 1.0F;
    private static final double GRAVITY = 0.015;

    public float spin;
    public float prevSpin;

    public BladeshroomCapEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
        this.setDamage(DAMAGE);
    }

    public BladeshroomCapEntity(World world, LivingEntity thrower) {
        super(world, thrower);
        this.setDamage(DAMAGE);
    }

    public BladeshroomCapEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setDamage(DAMAGE);
    }

    @Override
    public void tick() {
        super.tick();

        this.prevSpin = this.spin;

        if (!this.inGround) {
            this.spin += 25.0F;

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, 0.05 - GRAVITY, 0.0));
            }
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        try {
            HIT_ACTIVE.set(true);
            super.onHit(result);
        } finally {
            HIT_ACTIVE.set(false);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(MidnightItems.BLADESHROOM_CAP);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (!HIT_ACTIVE.get()) {
            return;
        }

        if (event.getSound() == SoundEvents.ENTITY_ARROW_HIT) {
            event.setSound(MidnightSounds.BLADESHROOM_CAP_HIT);
        }
    }
}
