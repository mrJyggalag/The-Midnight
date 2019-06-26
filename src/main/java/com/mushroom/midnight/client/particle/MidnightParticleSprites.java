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
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public class MidnightParticleSprites {
    public enum SpriteTypes {
        SPORE, GREEN_UNSTABLE_BUSH, BLUE_UNSTABLE_BUSH, LIME_UNSTABLE_BUSH,
        SPORCH_BOGSHROOM_1, SPORCH_BOGSHROOM_2, SPORCH_BOGSHROOM_3,
        SPORCH_NIGHTSHROOM_1, SPORCH_NIGHTSHROOM_2, SPORCH_NIGHTSHROOM_3,
        SPORCH_DEWSHROOM_1, SPORCH_DEWSHROOM_2, SPORCH_DEWSHROOM_3,
        SPORCH_VIRIDSHROOM_1, SPORCH_VIRIDSHROOM_2, SPORCH_VIRIDSHROOM_3,
        FURNACE_FLAME, FADING_SPORE;
    }

    private static final EnumMap<SpriteTypes, TextureAtlasSprite> sprites = new EnumMap<>(SpriteTypes.class);

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        for (SpriteTypes spriteType : SpriteTypes.values()) {
            sprites.put(spriteType, event.getMap().registerSprite(new ResourceLocation(Midnight.MODID, "particles/" + spriteType.name().toLowerCase(Locale.ROOT))));
        }
    }

    public static TextureAtlasSprite getSprite(SpriteTypes particleType) {
        TextureAtlasSprite sprite = sprites.get(particleType);
        if (sprite == null) {
            return Minecraft.getInstance().getTextureMapBlocks().getAtlasSprite("missingno");
        }
        return sprite;
    }
}
