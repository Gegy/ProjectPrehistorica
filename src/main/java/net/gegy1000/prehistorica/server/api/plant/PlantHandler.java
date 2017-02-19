package net.gegy1000.prehistorica.server.api.plant;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.EquisetitesPlant;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.OrontiumMackiiPlant;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.OsmundaPlant;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.PaleoclusiaPlant;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.TempskyaPlant;
import net.gegy1000.prehistorica.server.api.plant.cretaceous.TreeFernPlant;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantHandler {
    private static final Map<Plant, PlantSpawner> SPAWNERS = new HashMap<>();
    private static final Map<TimePeriod, Map<Biome, List<Plant>>> PERIOD_PLANTS = new HashMap<>();
    private static final Map<Plant, Block> BLOCKS = new HashMap<>();

    public static final Plant EQUISETITES = new EquisetitesPlant();
    public static final Plant ORONTIUM_MACKII = new OrontiumMackiiPlant();
    public static final Plant OSMUNDA = new OsmundaPlant();
    public static final Plant TEMPSKYA = new TempskyaPlant();
    public static final Plant TREE_FERN = new TreeFernPlant();
    public static final Plant PALEOCLUSIA = new PaleoclusiaPlant();

    public static void register() {
        try {
            for (Field field : PlantHandler.class.getDeclaredFields()) {
                Object obj = field.get(null);
                if (obj instanceof Plant) {
                    PlantHandler.registerPlant((Plant) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerPlant(Plant plant) {
        PlantBlock block = plant.createBlock();
        SPAWNERS.put(plant, block.getSpawner());
        BLOCKS.put(plant, block);
        BlockRegistry.registerBlock(block);
        Map<Biome, List<Plant>> periodPlants = PERIOD_PLANTS.computeIfAbsent(plant.getTimePeriod(), period -> new HashMap<>());
        BiomeDictionary.Type[] types = plant.getSpawnBiomeTypes();
        for (BiomeDictionary.Type type : types) {
            Biome[] biomes = BiomeDictionary.getBiomesForType(type);
            for (Biome biome : biomes) {
                List<Plant> biomePlants = periodPlants.computeIfAbsent(biome, b -> new ArrayList<>());
                if (!biomePlants.contains(plant)) {
                    biomePlants.add(plant);
                }
            }
        }
    }

    public static List<Plant> getPlantsToSpawn(TimePeriod period, Biome biome) {
        if (PERIOD_PLANTS.containsKey(period)) {
            return PERIOD_PLANTS.get(period).get(biome);
        }
        return null;
    }

    public static PlantSpawner getSpawner(Plant plant) {
        return SPAWNERS.get(plant);
    }

    public static Block getBlock(Plant plant) {
        return BLOCKS.get(plant);
    }
}
