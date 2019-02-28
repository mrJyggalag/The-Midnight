package com.mushroom.midnight.common.effect;

import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class TormentedEffect extends GenericEffect {
    private static final DamageSource TORMENTED_DAMAGE = new MidnightDamageSource("tormented").setDamageBypassesArmor().setDamageIsAbsolute();
    private double posX, posZ;

    public TormentedEffect() {
        super(true, 0x0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (!entity.world.isRemote) {
            if (entity.posX != this.posX || entity.posZ != this.posZ || entity.isSneaking() || entity.motionY > 0) {
                this.posX = entity.posX;
                this.posZ = entity.posZ;
                entity.attackEntityFrom(TORMENTED_DAMAGE, 0.5f);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
