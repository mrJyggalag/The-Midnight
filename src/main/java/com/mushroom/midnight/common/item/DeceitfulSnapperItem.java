package com.mushroom.midnight.common.item;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.ItemActivationMessage;
import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class DeceitfulSnapperItem extends Item {
    private static final DamageSource DECEITFUL_SNAPPER_DAMAGE = new MidnightDamageSource("deceitful_snapper");

    public DeceitfulSnapperItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isRemote && entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            if (player.world.rand.nextInt(3) == 0) {
                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 5, false, true));
                player.attackEntityFrom(DECEITFUL_SNAPPER_DAMAGE, player.world.rand.nextInt(3) + 1);
                player.sendStatusMessage(new TranslationTextComponent("status.midnight.snapped"), true);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                MidnightCriterion.SNAPPED_BY_SNAPPER.trigger(player);
                Midnight.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ItemActivationMessage(new ItemStack(MidnightItems.ADVANCEMENT_SNAPPER)));
            } else {
                super.onItemUseFinish(stack, world, entity);
            }
        }
        return stack;
    }
}
