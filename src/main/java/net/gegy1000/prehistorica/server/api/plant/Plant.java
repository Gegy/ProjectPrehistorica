package net.gegy1000.prehistorica.server.api.plant;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.minecraftforge.common.BiomeDictionary;

public interface Plant {
    PlantBlock createBlock();

    BiomeDictionary.Type[] getSpawnBiomeTypes();

    double getSpawnChance();

    TimePeriod getTimePeriod();
}
