package net.gegy1000.prehistorica.server.world.biome;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.minecraft.world.biome.Biome;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeRegistry {
    public static final Map<TimePeriod, Map<PrehistoricaBiomeType, List<PrehistoricaBiome>>> BIOMES = new HashMap<>();

    public static final PrehistoricaBiome CRETACEOUS_JUNGLE = PrehistoricaBiome.Builder.start(168, "cretaceous_jungle")
            .withTemperature(0.8F)
            .withHumidity(0.8F)
            .withHeightVariation(0.05F)
            .build();

    public static final PrehistoricaBiome CRETACEOUS_FOREST = PrehistoricaBiome.Builder.start(169, "cretaceous_forest")
            .withTemperature(0.5F)
            .withHumidity(0.4F)
            .withHeightVariation(0.05F)
            .build();

    public static final PrehistoricaBiome CRETACEOUS_DRY_FOREST = PrehistoricaBiome.Builder.start(170, "cretaceous_dry_forest")
            .withTemperature(1.0F)
            .withHumidity(0.1F)
            .withHeightVariation(0.05F)
            .build();

    public static final PrehistoricaBiome CRETACEOUS_SWAMPY_FOREST = PrehistoricaBiome.Builder.start(171, "cretaceous_swampy_forest")
            .withTemperature(0.8F)
            .withHumidity(0.9F)
            .withBaseHeight(-0.2F)
            .withHeightVariation(0.1F)
            .build();

    public static final PrehistoricaBiome CRETACEOUS_FOREST_HILLS = PrehistoricaBiome.Builder.start(172, "cretaceous_forest_hills")
            .withTemperature(0.5F)
            .withHumidity(0.4F)
            .withBaseHeight(0.4F)
            .withHeightVariation(0.2F)
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
        Biome.registerBiome(biome.getId(), biome.getBiomeName(), biome);

        Map<PrehistoricaBiomeType, List<PrehistoricaBiome>> types = BIOMES.computeIfAbsent(biome.getTimePeriod(), period -> new HashMap<>());
        List<PrehistoricaBiome> biomes = types.computeIfAbsent(biome.getType(), type -> new ArrayList<>());
        biomes.add(biome);
    }
}
