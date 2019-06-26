package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.Midnight;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class IntegrationJEI implements IModPlugin {
    private final ResourceLocation rl = new ResourceLocation(Midnight.MODID);
    @Override
    public ResourceLocation getPluginUid() {
        return rl;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }
}
