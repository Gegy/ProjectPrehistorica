package net.gegy1000.prehistorica.server.world.biome.generator;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.world.biome.BiomeRegistry;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaBiomeType;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrehistoricaBiomeProvider extends BiomeProvider {
    private GenLayer biomeGenerator;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private List<Biome> spawnableBiomes;

    public PrehistoricaBiomeProvider(TimePeriod period) {
        this.biomeCache = new BiomeCache(this);
        this.spawnableBiomes = new ArrayList<>(BiomeRegistry.getBiomes(period, PrehistoricaBiomeType.LANDMASS));
    }

    public PrehistoricaBiomeProvider(TimePeriod period, long seed) {
        this(period);
        GenLayer[] layers = PrehistoricaGenLayer.construct(period, seed);
        this.biomeGenerator = layers[0];
        this.biomeIndexLayer = layers[1];
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return this.spawnableBiomes;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getTemperatureAtHeight(float baseTemperature, int height) {
        return baseTemperature;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
        IntCache.resetIntCache();
        if (biomes == null || biomes.length < width * height) {
            biomes = new Biome[width * height];
        }
        int[] biomeIds = this.biomeGenerator.getInts(x, z, width, height);
        try {
            for (int i = 0; i < width * height; ++i) {
                biomes[i] = Biome.getBiomeForId(biomeIds[i]);
            }
            return biomes;
        } catch (Throwable throwable) {
            CrashReport report = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory category = report.makeCategory("RawBiomeBlock");
            category.addCrashSection("biomes[] size", biomes.length);
            category.addCrashSection("x", x);
            category.addCrashSection("z", z);
            category.addCrashSection("w", width);
            category.addCrashSection("h", height);
            throw new ReportedException(report);
        }
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, int x, int z, int width, int height) {
        return this.getBiomes(biomes, x, z, width, height, true);
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, int x, int z, int width, int height, boolean cacheFlag) {
        IntCache.resetIntCache();
        if (biomes == null || biomes.length < width * height) {
            biomes = new Biome[width * height];
        }
        if (cacheFlag && width == 16 && height == 16 && (x & 15) == 0 && (z & 15) == 0) {
            Biome[] cachedBiomes = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(cachedBiomes, 0, biomes, 0, width * height);
            return biomes;
        } else {
            int[] indexLayer = this.biomeIndexLayer.getInts(x, z, width, height);
            for (int i = 0; i < width * height; ++i) {
                biomes[i] = Biome.getBiome(indexLayer[i]);
            }
            return biomes;
        }
    }

    @Override
    public boolean areBiomesViable(int x, int y, int radius, List<Biome> allowed) {
        IntCache.resetIntCache();
        int areaX = x - radius >> 2;
        int areaY = y - radius >> 2;
        int j1 = x + radius >> 2;
        int k1 = y + radius >> 2;
        int areaWidth = j1 - areaX + 1;
        int areaHeight = k1 - areaY + 1;
        int[] biomes = this.biomeGenerator.getInts(areaX, areaY, areaWidth, areaHeight);
        try {
            for (int j2 = 0; j2 < areaWidth * areaHeight; ++j2) {
                Biome biome = Biome.getBiome(biomes[j2]);

                if (!allowed.contains(biome)) {
                    return false;
                }
            }

            return true;
        } catch (Throwable throwable) {
            CrashReport report = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory category = report.makeCategory("Layer");
            category.addCrashSection("Layer", this.biomeGenerator.toString());
            category.addCrashSection("x", x);
            category.addCrashSection("z", y);
            category.addCrashSection("radius", radius);
            category.addCrashSection("allowed", allowed);
            throw new ReportedException(report);
        }
    }

    @Override
    public BlockPos findBiomePosition(int x, int y, int z, List<Biome> allowed, Random rand) {
        IntCache.resetIntCache();
        int areaX = x - z >> 2;
        int areaY = y - z >> 2;
        int j1 = x + z >> 2;
        int k1 = y + z >> 2;
        int areaWidth = j1 - areaX + 1;
        int areaHeight = k1 - areaY + 1;
        int[] biomes = this.biomeGenerator.getInts(areaX, areaY, areaWidth, areaHeight);
        BlockPos biomePosition = null;
        int j2 = 0;

        for (int k2 = 0; k2 < areaWidth * areaHeight; ++k2) {
            int l2 = areaX + k2 % areaWidth << 2;
            int i3 = areaY + k2 / areaWidth << 2;
            Biome biome = Biome.getBiome(biomes[k2]);

            if (allowed.contains(biome) && (biomePosition == null || rand.nextInt(j2 + 1) == 0)) {
                biomePosition = new BlockPos(l2, 0, i3);
                ++j2;
            }
        }

        return biomePosition;
    }

    @Override
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        WorldTypeEvent.InitBiomeGens event = new WorldTypeEvent.InitBiomeGens(worldType, seed, original);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        return event.getNewBiomeGens();
    }
}
