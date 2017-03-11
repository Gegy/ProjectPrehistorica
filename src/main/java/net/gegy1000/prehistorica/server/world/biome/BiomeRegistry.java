package net.gegy1000.prehistorica.server.world.biome;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.plant.PlantRegistry;
import net.gegy1000.prehistorica.server.block.PrehistoricSandBlock;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeRegistry {
    public static final Map<TimePeriod, Map<PrehistoricaBiomeType, List<PrehistoricaBiome>>> BIOMES = new HashMap<>();

    public static final PrehistoricaBiome TRIASSIC_EUROPEAN_DESERT = PrehistoricaBiome.Builder.start(168, "triassic_european_desert")
            .withTemperature(1.0F)
            .withHumidity(0.0F)
            .withHeightVariation(0.05F)
            .withGrass(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withSoil(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withTimePeriod(TimePeriod.TRIASSIC)
            .build();

    public static final PrehistoricaBiome TRIASSIC_EUROPEAN_FOREST = PrehistoricaBiome.Builder.start(169, "triassic_european_forest")
            .withTemperature(0.8F)
            .withHumidity(1.0F)
            .withHeightVariation(0.05F)
            .withPlants(PlantRegistry.EQUISETITES, PlantRegistry.OSMUNDA)
            .withTimePeriod(TimePeriod.TRIASSIC)
            .build();

    public static final PrehistoricaBiome TRIASSIC_SANDY_BEACH = PrehistoricaBiome.Builder.start(170, "triassic_sandy_beach")
            .withTemperature(0.8F)
            .withHumidity(0.4F)
            .withBaseHeight(0.0F)
            .withHeightVariation(0.025F)
            .withTimePeriod(TimePeriod.TRIASSIC)
            .withGrass(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withSoil(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withType(PrehistoricaBiomeType.BEACH)
            .build();

    public static final PrehistoricaBiome TRIASSIC_SANDY_SHALLOWS = PrehistoricaBiome.Builder.start(171, "triassic_sandy_shallows")
            .withBaseHeight(-0.5F)
            .withHeightVariation(0.005F)
            .withTimePeriod(TimePeriod.TRIASSIC)
            .withGrass(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withSoil(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withType(PrehistoricaBiomeType.OCEAN_SHALLOWS)
            .build();

    public static final PrehistoricaBiome TRIASSIC_PANTHALASSIC_SEA = PrehistoricaBiome.Builder.start(172, "triassic_panthalassic_sea")
            .withBaseHeight(-1.0F)
            .withHeightVariation(0.2F)
            .withTimePeriod(TimePeriod.TRIASSIC)
            .withGrass(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withSoil(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withType(PrehistoricaBiomeType.OCEAN)
            .build();

    public static final PrehistoricaBiome TRIASSIC_PANTHALASSIC_DEPTHS = PrehistoricaBiome.Builder.start(173, "triassic_panthalassic_depths")
            .withBaseHeight(-1.8F)
            .withHeightVariation(0.2F)
            .withTimePeriod(TimePeriod.TRIASSIC)
            .withGrass(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withSoil(PrehistoricSandBlock.from(TimePeriod.TRIASSIC))
            .withType(PrehistoricaBiomeType.DEEP_OCEAN)
            .build();

    public static void register() {
        try {
            for (Field field : BiomeRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);

                if (value instanceof PrehistoricaBiome) {
                    BiomeRegistry.registerBiome((PrehistoricaBiome) value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PrehistoricaBiome> getBiomes(TimePeriod period, PrehistoricaBiomeType type) {
        return BIOMES.getOrDefault(period, new HashMap<>()).getOrDefault(type, new ArrayList<>());
    }

    private static void registerBiome(PrehistoricaBiome biome) {
        PrehistoricaBiomeType biomeType = biome.getType();

        Biome.registerBiome(biome.getId(), biome.getBiomeName(), biome);

        Map<PrehistoricaBiomeType, List<PrehistoricaBiome>> types = BIOMES.computeIfAbsent(biome.getTimePeriod(), period -> new HashMap<>());
        List<PrehistoricaBiome> biomes = types.computeIfAbsent(biomeType, type -> new ArrayList<>());
        biomes.add(biome);

        if (biomeType == PrehistoricaBiomeType.OCEAN || biomeType == PrehistoricaBiomeType.OCEAN_SHALLOWS || biomeType == PrehistoricaBiomeType.DEEP_OCEAN) {
            BiomeManager.oceanBiomes.add(biome);
        }
    }

    public static boolean is(int id, PrehistoricaBiomeType type) {
        Biome biome = Biome.getBiome(id);
        if (biome instanceof PrehistoricaBiome) {
            PrehistoricaBiome prehistoricaBiome = (PrehistoricaBiome) biome;
            return prehistoricaBiome.getType() == type;
        }
        return false;
    }
}
