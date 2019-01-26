package com.mushroom.midnight.client.model;

import com.mushroom.midnight.common.entity.creature.EntityNova;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNovaOld extends ModelBase {
    private ModelRenderer Face;
    private ModelRenderer rotChunks;
    private ModelRenderer Spike61;
    private ModelRenderer Spike11_1;
    private ModelRenderer Spike7;
    private ModelRenderer Spike9;
    private ModelRenderer Spike8;
    private ModelRenderer Spike2;
    private ModelRenderer Spike4;
    private ModelRenderer Spike14;
    private ModelRenderer Spike13;
    private ModelRenderer Spike16;
    private ModelRenderer Spike15;
    private ModelRenderer Spike10;
    private ModelRenderer Spike31;
    private ModelRenderer Spike12;
    private ModelRenderer Spike11;
    private ModelRenderer Spike51;
    private ModelRenderer ChunkTop1;
    private ModelRenderer ChunkTop2;
    private ModelRenderer ChunkTop3;
    private ModelRenderer ChunkTop4;
    private ModelRenderer ChunkDown1;
    private ModelRenderer ChunkDown2;
    private ModelRenderer ChunkDown3;
    private ModelRenderer ChunkDown4;
    private ModelRenderer Spike61Child;
    private ModelRenderer Spike31Child;
    private ModelRenderer Spike11Child;
    private ModelRenderer Spike51Child;

    public ModelNovaOld() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Spike8 = new ModelRenderer(this, 29, 36);
        this.Spike8.setRotationPoint(-7.199999809265137F, 14.899999618530273F, -0.800000011920929F);
        this.Spike8.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike8, 0.0F, 0.0F, -0.6373942494392395F);
        this.Spike9 = new ModelRenderer(this, 19, 50);
        this.Spike9.setRotationPoint(0.0F, 7.800000190734863F, -2.200000047683716F);
        this.Spike9.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike9, 0.0F, 1.0471975803375244F, -0.5462880730628967F);
        this.Spike51Child = new ModelRenderer(this, 0, 38);
        this.Spike51Child.setRotationPoint(-3.299999952316284F, 0.6000000238418579F, 0.699999988079071F);
        this.Spike51Child.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.Spike11 = new ModelRenderer(this, 14, 17);
        this.Spike11.setRotationPoint(-1.7999999523162842F, 3.799999952316284F, -1.5F);
        this.Spike11.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Spike7 = new ModelRenderer(this, 0, 51);
        this.Spike7.setRotationPoint(3.799999952316284F, 11.699999809265137F, -0.800000011920929F);
        this.Spike7.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike7, 0.0F, 0.0F, 0.6373942494392395F);
        this.Spike10 = new ModelRenderer(this, 57, 50);
        this.Spike10.setRotationPoint(-2.0F, 6.900000095367432F, -6.699999809265137F);
        this.Spike10.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike10, 0.0F, -1.0471975803375244F, 0.5462880730628967F);
        this.Spike51 = new ModelRenderer(this, 0, 28);
        this.Spike51.setRotationPoint(-5.400000095367432F, 10.199999809265137F, -1.2999999523162842F);
        this.Spike51.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Spike61Child = new ModelRenderer(this, 14, 44);
        this.Spike61Child.setRotationPoint(1.0F, 0.6000000238418579F, 0.699999988079071F);
        this.Spike61Child.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.Spike16 = new ModelRenderer(this, 89, 38);
        this.Spike16.setRotationPoint(-1.2999999523162842F, 17.5F, 1.5F);
        this.Spike16.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike16, 0.0F, -1.001644492149353F, 2.1855013370513916F);
        this.Spike31 = new ModelRenderer(this, 34, 17);
        this.Spike31.setRotationPoint(-2.200000047683716F, 14.899999618530273F, -1.5F);
        this.Spike31.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
        this.Spike14 = new ModelRenderer(this, 70, 38);
        this.Spike14.setRotationPoint(3.700000047683716F, 5.400000095367432F, 1.5F);
        this.Spike14.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike14, 0.0F, -1.001644492149353F, -0.5462880730628967F);
        this.Spike15 = new ModelRenderer(this, 83, 24);
        this.Spike15.setRotationPoint(5.300000190734863F, 15.5F, 1.5F);
        this.Spike15.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike15, 0.0F, -1.001644492149353F, 1.0927506685256958F);
        this.Spike13 = new ModelRenderer(this, 64, 24);
        this.Spike13.setRotationPoint(-5.900000095367432F, 3.700000047683716F, 6.0F);
        this.Spike13.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike13, 0.0F, 1.001644492149353F, 0.5462880730628967F);
        this.Spike12 = new ModelRenderer(this, 49, 38);
        this.Spike12.setRotationPoint(1.399999976158142F, 15.699999809265137F, -5.900000095367432F);
        this.Spike12.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike12, 0.0F, -1.0471975803375244F, -2.367539167404175F);
        this.Spike11Child = new ModelRenderer(this, 18, 26);
        this.Spike11Child.setRotationPoint(1.0F, -3.9000000953674316F, 0.699999988079071F);
        this.Spike11Child.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.Spike31Child = new ModelRenderer(this, 34, 25);
        this.Spike31Child.setRotationPoint(1.0F, 1.399999976158142F, 0.699999988079071F);
        this.Spike31Child.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.Spike2 = new ModelRenderer(this, 45, 24);
        this.Spike2.setRotationPoint(-5.900000095367432F, 4.300000190734863F, -1.600000023841858F);
        this.Spike2.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike2, 0.0F, 0.0F, 0.5462880730628967F);
        this.Face = new ModelRenderer(this, 0, 0);
        this.Face.setRotationPoint(-3.799999952316284F, 7.0F, -3.5999999046325684F);
        this.Face.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
        this.rotChunks = new ModelRenderer(this, 0, 0);
        this.rotChunks.setRotationPoint(4F, 4F, 4F);
        this.rotChunks.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Spike4 = new ModelRenderer(this, 41, 6);
        this.Spike4.setRotationPoint(1.600000023841858F, 7.400000095367432F, -1.600000023841858F);
        this.Spike4.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike4, 0.0F, 0.0F, -0.5462880730628967F);
        this.Spike61 = new ModelRenderer(this, 13, 33);
        this.Spike61.setRotationPoint(3.5999999046325684F, 10.199999809265137F, -1.2999999523162842F);
        this.Spike61.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Spike11_1 = new ModelRenderer(this, 38, 50);
        this.Spike11_1.setRotationPoint(-4.300000190734863F, 12.5F, -5.900000095367432F);
        this.Spike11_1.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(Spike11_1, 0.0F, -1.0471975803375244F, -0.8651596903800965F);
        this.ChunkTop1 = new ModelRenderer(this, 106, 22);
        this.ChunkTop1.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkTop1.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkTop2 = new ModelRenderer(this, 106, 22);
        this.ChunkTop2.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkTop2.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkTop3 = new ModelRenderer(this, 106, 22);
        this.ChunkTop3.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkTop3.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkTop4 = new ModelRenderer(this, 106, 22);
        this.ChunkTop4.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkTop4.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkDown1 = new ModelRenderer(this, 106, 22);
        this.ChunkDown1.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkDown1.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkDown2 = new ModelRenderer(this, 106, 22);
        this.ChunkDown2.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkDown2.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkDown3 = new ModelRenderer(this, 107, 5);
        this.ChunkDown3.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkDown3.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ChunkDown4 = new ModelRenderer(this, 107, 5);
        this.ChunkDown4.setRotationPoint(4.0F, 4.0F, 4.0F);
        this.ChunkDown4.addBox(-2.5F, -18.0F, -2.5F, 5, 5, 5, 0.0F);

        this.Spike51.addChild(this.Spike51Child);
        this.Spike61.addChild(this.Spike61Child);
        this.Spike11.addChild(this.Spike11Child);
        this.Spike31.addChild(this.Spike31Child);
        this.rotChunks.addChild(this.ChunkTop1);
        this.rotChunks.addChild(this.ChunkTop2);
        this.rotChunks.addChild(this.ChunkTop3);
        this.rotChunks.addChild(this.ChunkTop4);
        this.rotChunks.addChild(this.ChunkDown1);
        this.rotChunks.addChild(this.ChunkDown2);
        this.rotChunks.addChild(this.ChunkDown3);
        this.rotChunks.addChild(this.ChunkDown4);
        this.Face.addChild(this.rotChunks);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Spike8.render(f5);
        this.Spike9.render(f5);
        this.Spike11.render(f5);
        this.Spike7.render(f5);
        this.Spike10.render(f5);
        this.Spike51.render(f5);
        this.Spike16.render(f5);
        this.Spike31.render(f5);
        this.Spike14.render(f5);
        this.Spike15.render(f5);
        this.Spike13.render(f5);
        this.Spike12.render(f5);
        this.Spike2.render(f5);
        this.Face.render(f5);
        this.Spike4.render(f5);
        this.Spike61.render(f5);
        this.Spike11_1.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        float angle = (ageInTicks * 0.4f) / (2f * (float) Math.PI);
        ChunkTop1.rotateAngleX = ChunkTop1.rotateAngleZ = ChunkDown1.rotateAngleZ = angle;
        ChunkTop2.rotateAngleX = ChunkTop2.rotateAngleZ = ChunkDown2.rotateAngleZ = angle + (float) Math.PI * 0.25f;
        ChunkTop3.rotateAngleX = ChunkTop3.rotateAngleZ = ChunkDown3.rotateAngleZ = angle + (float) Math.PI * 0.5f;
        ChunkTop4.rotateAngleX = ChunkTop4.rotateAngleZ = ChunkDown4.rotateAngleZ = angle + (float) Math.PI * 0.75f;
        ChunkTop1.rotateAngleY = ChunkTop2.rotateAngleY = ChunkTop3.rotateAngleY = ChunkTop4.rotateAngleY = 0f;

        ChunkDown1.rotateAngleX = ChunkTop1.rotateAngleX + (float) Math.PI;
        ChunkDown2.rotateAngleX = ChunkTop2.rotateAngleX + (float) Math.PI;
        ChunkDown3.rotateAngleX = ChunkTop3.rotateAngleX + (float) Math.PI;
        ChunkDown4.rotateAngleX = ChunkTop4.rotateAngleX + (float) Math.PI;
        ChunkDown1.rotateAngleY = ChunkDown2.rotateAngleY = ChunkDown3.rotateAngleY = ChunkDown4.rotateAngleY = 0f;

        rotChunks.rotateAngleX = rotChunks.rotateAngleY = entity instanceof EntityNova && ((EntityNova)entity).isAttacking() ? -2f * angle : 0f;
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
