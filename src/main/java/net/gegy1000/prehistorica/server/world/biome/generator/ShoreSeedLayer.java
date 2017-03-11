package net.gegy1000.prehistorica.server.world.biome.generator;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaBiomeType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ShoreSeedLayer extends PrehistoricaGenLayer {
    public ShoreSeedLayer(TimePeriod period, long seed, GenLayer parent) {
        super(period, seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] biomes = IntCache.getIntCache(areaWidth * areaHeight);
        int[] parent = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        for (int deltaY = 0; deltaY < areaHeight; deltaY++) {
            for (int deltaX = 0; deltaX < areaWidth; deltaX++) {
                this.initChunkSeed(deltaX + areaX, deltaY + areaY);
                int index = this.getIndex(deltaX, deltaY, areaWidth);
                int parentIndex = this.getIndex(deltaX + 1, deltaY + 1, areaWidth + 2);
                int[] neighbours = this.getNeighbours(parent, deltaX, deltaY, areaWidth);
                int parentBiome = parent[parentIndex];
                biomes[index] = parentBiome;
                if (!isBiomeOceanic(parentBiome)) {
                    boolean beach = false;
                    for (int neighbour : neighbours) {
                        if (isBiomeOceanic(neighbour)) {
                            beach = true;
                            break;
                        }
                    }
                    if (beach) {
                        biomes[index] = Biome.getIdForBiome(this.selectBiome(PrehistoricaBiomeType.BEACH));
                    }
                }
            }
        }
        return biomes;
    }
}
