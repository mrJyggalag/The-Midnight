package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IntegrationJEI implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		// blacklisted items
		IIngredientBlacklist blackList = jeiHelpers.getIngredientBlacklist();
		blackList.addIngredientToBlacklist(new ItemStack(MidnightBlocks.DARK_WATER));
		blackList.addIngredientToBlacklist(new ItemStack(MidnightBlocks.MIASMA));
		blackList.addIngredientToBlacklist(new ItemStack(MidnightItems.ADVANCEMENT_SNAPPER));
		blackList.addIngredientToBlacklist(new ItemStack(MidnightItems.ADVANCEMENT_HIGHNESS));
		blackList.addIngredientToBlacklist(new ItemStack(MidnightItems.ROCKSHROOM_SHIELD));
	}
}
