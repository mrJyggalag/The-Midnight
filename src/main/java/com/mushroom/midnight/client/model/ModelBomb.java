package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBomb extends ModelBase {
    public ModelRenderer shape1;

    public ModelBomb() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0f, 0f, 0f);
        this.shape1.addBox(-8f, -8f, 0f, 16, 16, 0, 0f);
    }

    public void render() {
        this.shape1.render(0.0625f);
    }
}
