package com.mushroom.midnight.common.event;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
        Item[] pickaxes = {Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.STONE_PICKAXE, Items.WOODEN_PICKAXE};
        ItemStack iteminmainhand = player.getHeldItemMainhand();
        if (event.getState().getBlock() == ModBlocks.SUAVIS) {
            if (!player.isCreative()) {
                boolean haspickaxe = false;
                for (Item pickaxe: pickaxes) {
                    if (iteminmainhand.getItem() == pickaxe) {
                        haspickaxe = true;
                        boolean isenchanted = false;
                        if (iteminmainhand.isItemEnchanted()) {
                            isenchanted = true;
                            NBTTagList enchants = (NBTTagList) iteminmainhand.getEnchantmentTagList();
                            boolean hassilktouch = false;
                            for (int i = 0; i < enchants.tagCount(); i++) {
                                NBTTagCompound enchant = ((NBTTagList) enchants).getCompoundTagAt(i);
                                if (enchant.getInteger("id") == 33) {
                                    hassilktouch = true;
                                }
                            }
                            if (!hassilktouch) {
                                createNauseaCloud(world, pos);
                            }
                        }
                        if (!isenchanted) {
                            createNauseaCloud(world, pos);
                        }
                    }
                }
                if (!haspickaxe) {
                    createNauseaCloud(world, pos);
                }
            }
        }
    }

    private static void createNauseaCloud(World world, BlockPos pos) {
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
