package com.mushroom.midnight.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BombModel extends Model {
    public RendererModel shape1;

    public BombModel() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape1 = new RendererModel(this, 0, 0);
        this.shape1.setRotationPoint(0f, 0f, 0f);
        this.shape1.addBox(-8f, -8f, 0f, 16, 16, 0, 0f);
    }

    public void render() {
        this.shape1.render(0.0625f);
    }
}
