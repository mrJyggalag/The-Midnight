package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.entity.EntityCloud;
import com.mushroom.midnight.common.entity.projectile.EntitySporeBomb;
import com.mushroom.midnight.common.registry.ModEffects;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSporeBomb extends Item {
    public static int MAX_FUSE_TIME = 200;

    public enum BombType {
        NIGHTSHROOM(0x6F3FD6),
        DEWSHROOM(0x69D9E5),
        VIRIDSHROOM(0x3FD647),
        BOGSHROOM(0xE6742D);
        private final int color;

        BombType(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static BombType getDefault() {
            return NIGHTSHROOM;
        }

        public static BombType fromId(int id) {
            return values()[MathHelper.clamp(id, 0, values().length - 1)];
        }

        public static BombType fromStack(ItemStack stack) {
            return fromId(stack.getMetadata());
        }
    }

    public ItemSporeBomb() {
        super();
        setCreativeTab(ModTabs.MIDNIGHT_ITEMS);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new ItemSporeBomb.DispenserBehavior());
        hasSubtypes = true;
        setMaxStackSize(1);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (BombType bomb : BombType.values()) {
                items.add(new ItemStack(this, 1, bomb.ordinal()));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + "." + BombType.fromId(stack.getMetadata()).name().toLowerCase();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() == this) {
            player.getCooldownTracker().setCooldown(this, 10);
            if (!world.isRemote) {
                EntitySporeBomb bomb = createEntityBomb(world, heldItem, player);
                bomb.shoot(player, player.rotationPitch, player.rotationYaw, 0f, 1.5f, 1f);
                world.spawnEntity(bomb);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
                if (!player.capabilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slotId, boolean isSelected) {
        if (checkExplode(world, stack)) {
            explode(BombType.fromId(stack.getMetadata()), world, entity.posX, entity.posY, entity.posZ);
            stack.shrink(1);
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        boolean valid = checkExplode(entityItem.world, stack);
        if (valid) {
            explode(BombType.fromId(stack.getMetadata()), entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ);
            entityItem.setDead();
        }
        return valid;
    }

    public static void setFuseTime(World world, ItemStack stack, int time) {
        if (stack.getItem() == ModItems.SPORE_BOMB) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound == null) {
                stack.setTagCompound(compound = new NBTTagCompound());
            }
            compound.setLong("fuse_time", world.getTotalWorldTime() + time);
        }
    }

    public static long getFuseTime(World world, ItemStack stack) {
        // TODO fusing animation on client
        return stack.getItem() == ModItems.SPORE_BOMB && stack.getTagCompound() != null && stack.getTagCompound().hasKey("fuse_time", Constants.NBT.TAG_LONG) ? Math.max(0, stack.getTagCompound().getLong("fuse_time") - world.getTotalWorldTime()) : 0;
    }

    public static boolean checkExplode(World world, ItemStack stack) {
        return !world.isRemote && stack.getItem() == ModItems.SPORE_BOMB && stack.getTagCompound() != null && stack.getTagCompound().hasKey("fuse_time", Constants.NBT.TAG_LONG) && world.getTotalWorldTime() >= stack.getTagCompound().getLong("fuse_time");
    }

    public static void explode(BombType bombType, World world, double x, double y, double z) {
        EntityCloud entity;
        //float explosionRadius = 2.5f;
        //world.createExplosion(null, x, y, z, explosionRadius, false);
        switch (bombType) {
            case NIGHTSHROOM:
                entity = createLingeringCloud(bombType, world, x, y, z);
                entity.setParticle(EnumParticleTypes.SPELL);
                entity.addEffect(new PotionEffect(ModEffects.DARKNESS, 100, 0, false, true));
                world.spawnEntity(entity);
                break;
            case DEWSHROOM:
                entity = createLingeringCloud(bombType, world, x, y, z);
                entity.setParticle(EnumParticleTypes.FALLING_DUST);
                entity.setRadiusPerTick(entity.getRadius() * 0.001f);
                entity.addEffect(new PotionEffect(ModEffects.STUNNED, 100, 0, false, true));
                entity.addEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5, false, true));
                world.spawnEntity(entity);
                break;
            case VIRIDSHROOM:
                entity = createLingeringCloud(bombType, world, x, y, z).setAllowTeleport();
                entity.setParticle(EnumParticleTypes.SLIME);
                entity.addEffect(new PotionEffect(ModEffects.TORMENTED, 100, 0, false, true));
                world.spawnEntity(entity);
                break;
            case BOGSHROOM:
                entity = createLingeringCloud(bombType, world, x, y, z);
                entity.setParticle(EnumParticleTypes.SPELL_MOB);
                entity.addEffect(new PotionEffect(ModEffects.CONFUSION, 200, 0, false, true));
                world.spawnEntity(entity);
                break;
        }
    }

    private static EntityCloud createLingeringCloud(BombType bombType, World world, double x, double y, double z) {
        EntityCloud entity = new EntityCloud(world, x, y, z);
        entity.setRadius(2.5f);
        //entity.setRadiusOnUse(-0.5f);
        entity.setWaitTime(10);
        entity.setDuration(entity.getDuration() / 2);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(PotionTypes.EMPTY);
        entity.setColor(bombType.getColor());
        entity.setParticle(EnumParticleTypes.SLIME);
        return entity;
    }

    @SideOnly(Side.CLIENT)
    public void renderModel() {
        for (BombType bomb : BombType.values()) {
            ModelLoader.setCustomModelResourceLocation(this, bomb.ordinal(), new ModelResourceLocation(getRegistryName() + "_" + bomb.ordinal(), "inventory"));
        }
    }

    private static class DispenserBehavior extends BehaviorProjectileDispense {
        @Override
        protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack stack) {
            return createEntityBomb(world, stack, pos);
        }
    }

    private static EntitySporeBomb createEntityBomb(World world, ItemStack stack, EntityPlayer player) {
        EntitySporeBomb bomb = new EntitySporeBomb(world, player);
        return addStackToBomb(bomb, stack);
    }

    private static EntitySporeBomb createEntityBomb(World world, ItemStack stack, IPosition pos) {
        EntitySporeBomb bomb = new EntitySporeBomb(world, pos.getX(), pos.getY(), pos.getZ());
        return addStackToBomb(bomb, stack);
    }

    private static EntitySporeBomb addStackToBomb(EntitySporeBomb bomb, ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        ItemStack newStack = stack.copy();
        if (compound == null || !compound.hasKey("fuse_time", Constants.NBT.TAG_LONG)) {
            setFuseTime(bomb.world, newStack, MAX_FUSE_TIME);
        }
        bomb.setBombStack(newStack);
        return bomb;
    }
}
