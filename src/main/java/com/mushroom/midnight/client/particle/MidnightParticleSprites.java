package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.Midnight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class MidnightParticleSprites {
    private static TextureAtlasSprite sporeSprite;

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        sporeSprite = event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/spore"));
    }

    @Nonnull
    public static TextureAtlasSprite getSporeSprite() {
        if (sporeSprite == null) {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("missingno");
        }
        return sporeSprite;
    }
}
