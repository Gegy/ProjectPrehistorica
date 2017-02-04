package net.gegy1000.prehistorica.server.api.plant.cretaceous;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.plant.Plant;
import net.gegy1000.prehistorica.server.api.plant.PlantSpawner;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.minecraftforge.common.BiomeDictionary;

public class OrontiumMackiiPlant implements Plant {
    @Override
    public PlantBlock createBlock() {
        return new PlantBlock("orontium_mackii").withSpawner(PlantSpawner.SINGLE_GROUND).withBounds(PlantBlock.BUSH);
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
