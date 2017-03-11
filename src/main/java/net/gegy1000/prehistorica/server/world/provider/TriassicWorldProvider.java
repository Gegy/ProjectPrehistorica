package net.gegy1000.prehistorica.server.world.provider;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.util.PrehistoricaUtils;
import net.gegy1000.prehistorica.server.world.biome.generator.PrehistoricaBiomeProvider;
import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.gegy1000.prehistorica.server.world.generator.TriassicChunkGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class TriassicWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return DimensionRegistry.TRIASSIC;
    }

    @Override
    public void createBiomeProvider() {
        this.biomeProvider = new PrehistoricaBiomeProvider(TimePeriod.TRIASSIC, this.world.getSeed());
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new TriassicChunkGenerator(this.world, this.world.getSeed(), this.world.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        float angle = this.calculateCelestialAngle(cameraEntity.world.getWorldTime(), partialTicks);
        float brightness = MathHelper.cos(angle * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        brightness = MathHelper.clamp(brightness, 0.0F, 1.0F);
        Vec3d base = super.getSkyColor(cameraEntity, partialTicks);
        return PrehistoricaUtils.blendColor(base, new Vec3d(0.1, 0.4, 0.2), brightness * 0.5F);
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean canDropChunk(int x, int z) {
        return !this.world.isSpawnChunk(x, z) || !this.world.provider.getDimensionType().shouldLoadSpawn();
    }
}