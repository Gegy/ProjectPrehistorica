package net.gegy1000.prehistorica.server.world.biome.generator;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.world.biome.BiomeRegistry;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaBiome;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaBiomeType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

import java.util.ArrayList;
import java.util.List;

public abstract class PrehistoricaGenLayer extends GenLayer {
    private TimePeriod period;

    public PrehistoricaGenLayer(TimePeriod period, long seed) {
        super(seed);
        this.period = period;
    }

    public static GenLayer[] construct(TimePeriod period, long seed) {
        GenLayer generator = new SeedLandLayer(period, 1);
        generator = new GenLayerFuzzyZoom(1000, generator);
        generator = new BiomeSeedLayer(period, 2, generator);
        generator = new GenLayerFuzzyZoom(2000, generator);
        generator = new GenLayerFuzzyZoom(3000, generator);

        generator = new ShallowsSeedLayer(period, 5, generator);
        generator = new DeepSeedLayer(period, 4, generator);
        generator = new GenLayerFuzzyZoom(4000, generator);
        generator = new GenLayerZoom(5000, generator);
        generator = new ShoreSeedLayer(period, 6, generator);
        generator = new GenLayerZoom(6000, generator);
        generator = new GenLayerZoom(7000, generator);

        return PrehistoricaGenLayer.from(generator, seed);
    }

    private static GenLayer[] from(GenLayer layer, long seed) {
        GenLayer zoom = new GenLayerVoronoiZoom(45L, layer);
        layer.initWorldGenSeed(seed);
        zoom.initWorldGenSeed(seed);
        return new GenLayer[] { layer, zoom };
    }

    protected int getIndex(int deltaX, int deltaZ, int areaWidth) {
        return deltaX + deltaZ * areaWidth;
    }

    protected List<PrehistoricaBiome> getBiomes(PrehistoricaBiomeType type) {
        return BiomeRegistry.getBiomes(this.period, type);
    }

    protected Biome selectBiome(PrehistoricaBiomeType... types) {
        List<PrehistoricaBiome> biomes = new ArrayList<>();
        for (PrehistoricaBiomeType type : types) {
            biomes.addAll(this.getBiomes(type));
        }
        int total = 0;
        for (PrehistoricaBiome biome : biomes) {
            total += biome.getGenerationChance();
        }
        int selection = this.nextInt(total);
        total = 0;
        for (PrehistoricaBiome biome : biomes) {
            total += biome.getGenerationChance();
            if (total >= selection) {
                return biome;
            }
        }
        return biomes.get(0);
    }

    protected void set(Biome biome, int[] data, int x, int y, int width) {
        data[this.getIndex(x, y, width)] = Biome.getIdForBiome(biome);
    }

    protected int[] getNeighbours(int[] parentBiomes, int deltaX, int deltaY, int areaWidth) {
        int parentDeltaX = deltaX + 1;
        int parentDeltaY = deltaY + 1;
        int parentAreaWidth = areaWidth + 2;
        int north = parentBiomes[parentDeltaX + (parentDeltaY - 1) * parentAreaWidth];
        int west = parentBiomes[parentDeltaX - 1 + parentDeltaY * parentAreaWidth];
        int east = parentBiomes[parentDeltaX + 1 + parentDeltaY * parentAreaWidth];
        int south = parentBiomes[parentDeltaX + (parentDeltaY + 1) * parentAreaWidth];
        return new int[] { north, east, west, south };
    }
}
