package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSuavis extends Block implements IModelProvider {

    public BlockSuavis() {
        super(Material.GOURD, MapColor.LIGHT_BLUE);
        this.setLightLevel(0.8F);
        this.setCreativeTab(Midnight.DECORATION_TAB);
        setHardness(1f);
        this.setSoundType(SoundType.SLIME);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.RAW_SUAVIS;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(world, pos, state, player);

        if (!world.isRemote && !player.isCreative()) {
            ItemStack heldItem = player.getHeldItemMainhand();
            int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem);
            if (silkTouchLevel <= 0) {
                createNauseaCloud(world, pos);
            }
            if (player instanceof EntityPlayerMP) {
                ModCriterion.HARVESTED_SUAVIS.trigger((EntityPlayerMP) player);
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
        entity.setColor(0x355796);
        entity.addEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 30, 0, false, true));
        world.spawnEntity(entity);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 2), 1, 4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }
}
