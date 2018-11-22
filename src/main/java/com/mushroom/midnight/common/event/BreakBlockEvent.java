package com.mushroom.midnight.common.event;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakBlockEvent {

    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (event.getState().getBlock() == ModBlocks.SUAVIS) {
            if (!player.isCreative()) {
                EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());
                entity.setRadius(3.0F);
                entity.setRadiusOnUse(-0.5F);
                entity.setWaitTime(10);
                entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
                entity.setPotion(PotionTypes.EMPTY);
                entity.setColor(3495830);
                entity.addEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 30, 0, false, true));
                world.spawnEntity(entity);
            }
        }
    }
}
