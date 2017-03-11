package net.gegy1000.prehistorica.server.world.biome.generator;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaBiomeType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class BiomeSeedLayer extends PrehistoricaGenLayer {
    public BiomeSeedLayer(TimePeriod period, long seed, GenLayer parent) {
        super(period, seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] biomes = IntCache.getIntCache(areaWidth * areaHeight);
        int[] parent = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        for (int deltaY = 0; deltaY < areaHeight; deltaY++) {
            for (int deltaX = 0; deltaX < areaWidth; deltaX++) {
                this.initChunkSeed(deltaX + areaX, deltaY + areaY);
                int index = this.getIndex(deltaX, deltaY, areaWidth);
                if (parent[index] == 1) {
                    this.set(this.selectBiome(PrehistoricaBiomeType.LANDMASS), biomes, deltaX, deltaY, areaWidth);
                } else {
                    biomes[index] = Biome.getIdForBiome(this.selectBiome(PrehistoricaBiomeType.OCEAN));
                }
            }
        }
        return biomes;
    }
}
