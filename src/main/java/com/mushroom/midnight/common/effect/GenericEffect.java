package com.mushroom.midnight.common.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

public class GenericEffect extends Effect {
    private ResourceLocation icon = null;

    public GenericEffect(EffectType type, int color) {
        super(type, color);
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
    @OnlyIn(Dist.CLIENT)
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z) {
        if (icon != null && gui != null) {
            gui.getMinecraft().getTextureManager().bindTexture(icon);
            Screen.blit(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft client = Minecraft.getInstance();
        if (icon != null && !client.gameSettings.showDebugInfo) {
            client.getTextureManager().bindTexture(icon);
            Screen.blit(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public final String getName() {
        return "potion." + super.getName() + ".name";
    }
}
