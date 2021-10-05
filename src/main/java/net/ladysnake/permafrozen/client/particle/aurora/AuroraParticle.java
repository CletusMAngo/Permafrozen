package net.ladysnake.permafrozen.client.particle.aurora;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import java.util.Random;

public class AuroraParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    private final float redEvolution;
    private final float greenEvolution;
    private final float blueEvolution;

    private AuroraParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, AuroraParticleEffect wispTrailParticleEffect, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.colorRed = wispTrailParticleEffect.getRed();
        this.colorGreen = wispTrailParticleEffect.getGreen();
        this.colorBlue = wispTrailParticleEffect.getBlue();
        this.redEvolution = wispTrailParticleEffect.getRedEvolution();
        this.greenEvolution = wispTrailParticleEffect.getGreenEvolution();
        this.blueEvolution = wispTrailParticleEffect.getBlueEvolution();
        this.maxAge = 10 + this.random.nextInt(10);
        this.scale *= 0.75f + new Random().nextFloat() * 0.50f;
        this.setSpriteForAge(spriteProvider);
        this.velocityY = 0.1;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        // fade and die
        if (this.age++ >= this.maxAge) {
            colorAlpha -= 0.05f;
        }
        if (colorAlpha < 0f || this.scale <= 0f) {
            this.markDead();
        }

//        float redEv = -0.03f;
//        float greenEv = 0.0f;
//        float blueEv = -0.01f;
//        colorRed = MathHelper.clamp(colorRed+redEv, 0, 1);
//        colorGreen = MathHelper.clamp(colorGreen+greenEv, 0, 1);
//        colorBlue = MathHelper.clamp(colorBlue+blueEv, 0, 1);

        colorRed = MathHelper.clamp(colorRed + redEvolution, 0, 1);
        colorGreen = MathHelper.clamp(colorGreen + greenEvolution, 0, 1);
        colorBlue = MathHelper.clamp(colorBlue + blueEvolution, 0, 1);

        this.velocityY = 0.3;
        this.velocityX = 0;
        this.velocityZ = 0;
        this.scale = Math.max(0, this.scale - 0.005f);
        this.move(velocityX, velocityY, velocityZ);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternion quaternion2;
        if (this.angle == 0.0F) {
            quaternion2 = camera.getRotation();
        } else {
            quaternion2 = new Quaternion(camera.getRotation());
            float i = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
            quaternion2.hamiltonProduct(Vec3f.POSITIVE_Z.getRadialQuaternion(i));
        }

        Vec3f vector3f = new Vec3f(-1.0F, -1.0F, 0.0F);
        vector3f.rotate(quaternion2);
        Vec3f[] vector3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float j = this.getSize(tickDelta);

        for (int k = 0; k < 4; ++k) {
            Vec3f vector3f2 = vector3fs[k];
            vector3f2.rotate(quaternion2);
            vector3f2.scale(j);
            vector3f2.add(f, g, h);
        }

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();
        int l = 15728880;

        vertexConsumer.vertex(vector3fs[0].getX(), vector3fs[0].getY(), vector3fs[0].getZ()).texture(maxU, maxV).color(colorRed, colorGreen, colorBlue, colorAlpha).light(l).next();
        vertexConsumer.vertex(vector3fs[1].getX(), vector3fs[1].getY(), vector3fs[1].getZ()).texture(maxU, minV).color(colorRed, colorGreen, colorBlue, colorAlpha).light(l).next();
        vertexConsumer.vertex(vector3fs[2].getX(), vector3fs[2].getY(), vector3fs[2].getZ()).texture(minU, minV).color(colorRed, colorGreen, colorBlue, colorAlpha).light(l).next();
        vertexConsumer.vertex(vector3fs[3].getX(), vector3fs[3].getY(), vector3fs[3].getZ()).texture(minU, maxV).color(colorRed, colorGreen, colorBlue, colorAlpha).light(l).next();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<AuroraParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(AuroraParticleEffect auroraParticleEffect, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new AuroraParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, auroraParticleEffect, this.spriteProvider);
        }
    }
}
