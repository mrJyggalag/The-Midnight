package com.mushroom.midnight.common.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mushroom.midnight.Midnight.MODID;

public abstract class GenericEffect extends Potion {
	private ResourceLocation icon = null;

	protected GenericEffect(boolean isBadEffect, int color) {
		super(isBadEffect, color);
	}

	public GenericEffect withIcon(String name) {
		icon = new ResourceLocation(MODID, "textures/potions/" + name + ".png");
		return this;
	}

	public GenericEffect withIcon(ResourceLocation rl) {
		icon = new ResourceLocation(rl.getNamespace(), "textures/potions/" + rl.getPath() + ".png");
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if (icon != null && mc.currentScreen != null) {
			mc.renderEngine.bindTexture(icon);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		if (icon != null && !mc.gameSettings.showDebugInfo) {
			mc.renderEngine.bindTexture(icon);
			Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
		}
	}

	@Override
	public final String getName() {
		return "potion." + super.getName() + ".name";
	}
}
