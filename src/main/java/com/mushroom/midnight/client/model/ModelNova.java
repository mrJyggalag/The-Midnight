package com.mushroom.midnight.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNova extends ModelBase {
    private final ModelRenderer Face;
    private final ModelRenderer Spike11;
    private final ModelRenderer Spike2;
    private final ModelRenderer Spike31;
    private final ModelRenderer Spike4;
    private final ModelRenderer Spike51;
    private final ModelRenderer Spike61;
    private final ModelRenderer Spike7;
    private final ModelRenderer Spike8;
    private final ModelRenderer Spike9;
    private final ModelRenderer Spike10;
    private final ModelRenderer Spike11_1;
    private final ModelRenderer Spike12;
    private final ModelRenderer Spike13;
    private final ModelRenderer Spike14;
    private final ModelRenderer Spike15;
    private final ModelRenderer Spike16;
    private final ModelRenderer Chunk1;
    private final ModelRenderer Chunk2;
    private final ModelRenderer Chunk3;
    private final ModelRenderer Chunk4;
    private final ModelRenderer Chunk5;
    private final ModelRenderer Spike12_1;
    private final ModelRenderer Spike32;
    private final ModelRenderer Spike52;
    private final ModelRenderer Spike62;

    public ModelNova() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Face = new ModelRenderer(this, 0, 0);
        this.Face.setRotationPoint(-3.8F, 7.0F, -3.6F);
        this.Face.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
        this.Spike51 = new ModelRenderer(this, 0, 28);
        this.Spike51.setRotationPoint(-5.4F, 10.2F, -1.3F);
        this.Spike51.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Chunk4 = new ModelRenderer(this, 106, 22);
        this.Chunk4.setRotationPoint(-1.9F, 14.0F, -15.1F);
        this.Chunk4.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Spike4 = new ModelRenderer(this, 41, 6);
        this.Spike4.setRotationPoint(1.6F, 7.4F, -1.6F);
        this.Spike4.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike4, 0.0F, 0.0F, -0.5462880558742251F);
        this.Spike32 = new ModelRenderer(this, 34, 25);
        this.Spike32.setRotationPoint(1.0F, 1.4F, 0.7F);
        this.Spike32.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.Spike14 = new ModelRenderer(this, 70, 38);
        this.Spike14.setRotationPoint(3.7F, 5.4F, 1.5F);
        this.Spike14.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike14, 0.0F, -1.0016444577195458F, -0.5462880558742251F);
        this.Spike62 = new ModelRenderer(this, 14, 44);
        this.Spike62.setRotationPoint(1.0F, 0.6F, 0.7F);
        this.Spike62.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.Spike11 = new ModelRenderer(this, 14, 17);
        this.Spike11.setRotationPoint(-1.8F, 3.8F, -1.5F);
        this.Spike11.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Spike12_1 = new ModelRenderer(this, 18, 26);
        this.Spike12_1.setRotationPoint(1.0F, -3.9F, 0.7F);
        this.Spike12_1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.Spike31 = new ModelRenderer(this, 34, 17);
        this.Spike31.setRotationPoint(-2.2F, 14.9F, -1.5F);
        this.Spike31.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
        this.Spike2 = new ModelRenderer(this, 45, 24);
        this.Spike2.setRotationPoint(-5.9F, 4.3F, -1.6F);
        this.Spike2.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike2, 0.0F, 0.0F, 0.5462880558742251F);
        this.Spike52 = new ModelRenderer(this, 0, 38);
        this.Spike52.setRotationPoint(-3.3F, 0.6F, 0.7F);
        this.Spike52.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.Spike10 = new ModelRenderer(this, 57, 50);
        this.Spike10.setRotationPoint(-2.0F, 6.9F, -6.7F);
        this.Spike10.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike10, 0.0F, -1.0471975511965976F, 0.5462880558742251F);
        this.Spike61 = new ModelRenderer(this, 13, 33);
        this.Spike61.setRotationPoint(3.6F, 10.2F, -1.3F);
        this.Spike61.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Spike8 = new ModelRenderer(this, 29, 36);
        this.Spike8.setRotationPoint(-7.2F, 14.9F, -0.8F);
        this.Spike8.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike8, 0.0F, 0.0F, -0.6373942428283291F);
        this.Chunk2 = new ModelRenderer(this, 86, 5);
        this.Chunk2.setRotationPoint(5.7F, -6.7F, -5.4F);
        this.Chunk2.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Spike7 = new ModelRenderer(this, 0, 51);
        this.Spike7.setRotationPoint(3.8F, 11.7F, -0.8F);
        this.Spike7.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike7, 0.0F, 0.0F, 0.6373942428283291F);
        this.Spike13 = new ModelRenderer(this, 64, 24);
        this.Spike13.setRotationPoint(-5.9F, 3.7F, 6.0F);
        this.Spike13.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike13, 0.0F, 1.0016444577195458F, 0.5462880558742251F);
        this.Spike15 = new ModelRenderer(this, 83, 24);
        this.Spike15.setRotationPoint(5.3F, 15.5F, 1.5F);
        this.Spike15.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike15, 0.0F, -1.0016444577195458F, 1.0927506446736497F);
        this.Chunk1 = new ModelRenderer(this, 61, 5);
        this.Chunk1.setRotationPoint(-9.5F, -1.4F, -12.2F);
        this.Chunk1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Chunk5 = new ModelRenderer(this, 98, 49);
        this.Chunk5.setRotationPoint(-19.4F, 14.9F, -1.2F);
        this.Chunk5.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Chunk3 = new ModelRenderer(this, 107, 5);
        this.Chunk3.setRotationPoint(12.7F, 9.1F, -0.6F);
        this.Chunk3.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Spike9 = new ModelRenderer(this, 19, 50);
        this.Spike9.setRotationPoint(0.0F, 7.8F, -2.2F);
        this.Spike9.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike9, 0.0F, 1.0471975511965976F, -0.5462880558742251F);
        this.Spike11_1 = new ModelRenderer(this, 38, 50);
        this.Spike11_1.setRotationPoint(-4.3F, 12.5F, -5.9F);
        this.Spike11_1.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike11_1, 0.0F, -1.0471975511965976F, -0.8651597102135892F);
        this.Spike12 = new ModelRenderer(this, 49, 38);
        this.Spike12.setRotationPoint(1.4F, 15.7F, -5.9F);
        this.Spike12.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike12, 0.0F, -1.0471975511965976F, -2.367539130330308F);
        this.Spike16 = new ModelRenderer(this, 89, 38);
        this.Spike16.setRotationPoint(-1.3F, 17.5F, 1.5F);
        this.Spike16.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(this.Spike16, 0.0F, -1.0016444577195458F, 2.1855012893472994F);
        this.Spike31.addChild(this.Spike32);
        this.Spike61.addChild(this.Spike62);
        this.Spike11.addChild(this.Spike12_1);
        this.Spike51.addChild(this.Spike52);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, age, yaw, pitch, scale, entity);

        this.Face.render(scale);
        this.Spike51.render(scale);
        this.Chunk4.render(scale);
        this.Spike4.render(scale);
        this.Spike14.render(scale);
        this.Spike11.render(scale);
        this.Spike31.render(scale);
        this.Spike2.render(scale);
        this.Spike10.render(scale);
        this.Spike61.render(scale);
        this.Spike8.render(scale);
        this.Chunk2.render(scale);
        this.Spike7.render(scale);
        this.Spike13.render(scale);
        this.Spike15.render(scale);
        this.Chunk1.render(scale);
        this.Chunk5.render(scale);
        this.Chunk3.render(scale);
        this.Spike9.render(scale);
        this.Spike11_1.render(scale);
        this.Spike12.render(scale);
        this.Spike16.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale, Entity entity) {
    }

    private void setRotateAngle(ModelRenderer box, float x, float y, float z) {
        box.rotateAngleX = x;
        box.rotateAngleY = y;
        box.rotateAngleZ = z;
    }
}
