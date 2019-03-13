package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.entity.EntityCloud;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderCloud extends Render<EntityCloud> {

    public RenderCloud(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    @Nullable
    public ResourceLocation getEntityTexture(EntityCloud entity) {
        return null;
    }
}
