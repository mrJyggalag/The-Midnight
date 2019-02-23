package com.mushroom.midnight.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemUnstableFruit extends ItemFoodBasic {
    public enum FruitColor {
        BLUE(0.7f, 0.14f), LIME(0.4f, 0.2f), GREEN(0.1f, 0.33f);
        private final float poisonChance, levitationChance;
        FruitColor(float poisonChance, float levitationChance) {
            this.poisonChance = poisonChance;
            this.levitationChance = levitationChance;
        }
    }

    private final FruitColor fruitColor;

    public ItemUnstableFruit(FruitColor fruitColor) {
        super(1, 0.3f, false);
        this.fruitColor = fruitColor;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (world.rand.nextFloat() < this.fruitColor.poisonChance) {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, this.fruitColor.ordinal(), false, true));
        }
        if (world.rand.nextFloat() < this.fruitColor.levitationChance) {
            player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, this.fruitColor.ordinal(), false, true));
        }
    }
}
