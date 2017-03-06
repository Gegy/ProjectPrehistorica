package net.gegy1000.prehistorica.server.world.biome;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.block.PrehistoricGrassBlock;
import net.gegy1000.prehistorica.server.block.PrehistoricSoilBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

public class PrehistoricaBiome extends Biome {
    private int id;
    private TimePeriod timePeriod;
    private PrehistoricaBiomeType type;
    private int generationChance;

    public PrehistoricaBiome(int id, BiomeProperties properties) {
        super(properties);
        this.id = id;
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
    }

    public int getId() {
        return this.id;
    }

    public TimePeriod getTimePeriod() {
        return this.timePeriod;
    }

    public PrehistoricaBiomeType getType() {
        return this.type;
    }

    public int getGenerationChance() {
        return this.generationChance;
    }

    public static class Builder {
        private final int id;
        private BiomeProperties biomeProperties;
        private PrehistoricaBiomeType type = PrehistoricaBiomeType.LANDMASS;
        private TimePeriod timePeriod = TimePeriod.CRETACEOUS;
        private IBlockState grassBlock;
        private IBlockState soilBlock;
        private int generationChance = 10;

        private Builder(int id, String name) {
            this.id = id;
            this.biomeProperties = new BiomeProperties(name);
        }

        public static Builder start(int id, String name) {
            return new Builder(id, name);
        }

        public Builder withBaseHeight(float baseHeight) {
            this.biomeProperties.setBaseHeight(baseHeight);
            return this;
        }

        public Builder withHeightVariation(float heightVariation) {
            this.biomeProperties.setHeightVariation(heightVariation);
            return this;
        }

        public Builder withTimePeriod(TimePeriod timePeriod) {
            this.timePeriod = timePeriod;
            return this;
        }

        public Builder withGrass(IBlockState state) {
            this.grassBlock = state;
            return this;
        }

        public Builder withSoil(IBlockState state) {
            this.soilBlock = state;
            return this;
        }

        public Builder withHumidity(float humidity) {
            this.biomeProperties.setRainfall(humidity);
            return this;
        }

        public Builder withTemperature(float temperature) {
            this.biomeProperties.setTemperature(temperature);
            return this;
        }

        public Builder withType(PrehistoricaBiomeType type) {
            this.type = type;
            return this;
        }

        public Builder withChance(int chance) {
            this.generationChance = chance;
            return this;
        }

        public IBlockState getGrassBlock() {
            if (this.grassBlock == null) {
                return BlockRegistry.PREHISTORIC_GRASS.getDefaultState().withProperty(PrehistoricGrassBlock.PERIOD, this.timePeriod);
            }
            return this.grassBlock;
        }

        public IBlockState getSoilBlock() {
            if (this.soilBlock == null) {
                return BlockRegistry.PREHISTORIC_SOIL.getDefaultState().withProperty(PrehistoricSoilBlock.PERIOD, this.timePeriod);
            }
            return this.soilBlock;
        }

        public PrehistoricaBiome build() {
            PrehistoricaBiome biome = new PrehistoricaBiome(this.id, this.biomeProperties);
            biome.topBlock = this.getGrassBlock();
            biome.fillerBlock = this.getSoilBlock();
            biome.timePeriod = this.timePeriod;
            biome.type = this.type;
            biome.generationChance = this.generationChance;
            return biome;
        }
    }
}
