package net.gegy1000.prehistorica.server.world.biome.generator;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.minecraft.world.gen.layer.IntCache;

public class SeedLandLayer extends PrehistoricaGenLayer {
    public SeedLandLayer(TimePeriod period, long seed) {
        super(period, seed);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] biomes = IntCache.getIntCache(areaWidth * areaHeight);
        for (int deltaY = 0; deltaY < areaHeight; deltaY++) {
            for (int deltaX = 0; deltaX < areaWidth; deltaX++) {
                this.initChunkSeed(deltaX + areaX, deltaY + areaY);
                biomes[this.getIndex(deltaX, deltaY, areaWidth)] = this.nextInt(2) == 0 ? 1 : 0;
            }
        }
        return biomes;
    }
}
