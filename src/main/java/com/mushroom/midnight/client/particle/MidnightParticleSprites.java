package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.Midnight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumMap;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class MidnightParticleSprites {
    public enum SpriteTypes {
        SPORE, GREEN_UNSTABLE_BUSH, BLUE_UNSTABLE_BUSH, LIME_UNSTABLE_BUSH;
    }

    private static final EnumMap<SpriteTypes, TextureAtlasSprite> sprites = new EnumMap<>(SpriteTypes.class);

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        sprites.put(SpriteTypes.SPORE, event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/spore")));
        sprites.put(SpriteTypes.GREEN_UNSTABLE_BUSH, event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/green_unstable_bush")));
        sprites.put(SpriteTypes.BLUE_UNSTABLE_BUSH, event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/blue_unstable_bush")));
        sprites.put(SpriteTypes.LIME_UNSTABLE_BUSH, event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/lime_unstable_bush")));
    }

    public static TextureAtlasSprite getSprite(SpriteTypes particleType) {
        TextureAtlasSprite sprite = sprites.get(particleType);
        if (sprite == null) {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("missingno");
        }
        return sprite;
    }
}
