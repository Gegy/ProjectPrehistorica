package net.gegy1000.prehistorica.server.api.plant.cretaceous;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.plant.Plant;
import net.gegy1000.prehistorica.server.api.plant.PlantSpawner;
import net.gegy1000.prehistorica.server.block.plant.DoublePlantBlock;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.minecraftforge.common.BiomeDictionary;

public class TreeFernPlant implements Plant {
    @Override
    public PlantBlock createBlock() {
        return new DoublePlantBlock("tree_fern").withSpawner(PlantSpawner.DOUBLE_GROUND).withBounds(PlantBlock.DOUBLE);
    }

    @Override
    public BiomeDictionary.Type[] getSpawnBiomeTypes() {
        return new BiomeDictionary.Type[] { BiomeDictionary.Type.FOREST };
    }

    @Override
    public double getSpawnChance() {
        return 30;
    }

    @Override
    public TimePeriod getTimePeriod() {
        return TimePeriod.CRETACEOUS;
    }
}
