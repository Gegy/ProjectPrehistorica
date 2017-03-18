package net.gegy1000.prehistorica.server.world.biome;

import net.gegy1000.prehistorica.server.api.PrehistoricDimension;
import net.gegy1000.prehistorica.server.api.plant.Plant;
import net.gegy1000.prehistorica.server.api.plant.PlantRegistry;
import net.gegy1000.prehistorica.server.api.plant.PlantSpawner;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.List;
import java.util.Random;

public class PrehistoricaDecorator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        PrehistoricDimension dimension = DimensionRegistry.get(world.provider.getDimension());

        if (dimension != null) {
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;

            int plantCount = random.nextInt(16) + 4;
            for (int i = 0; i < plantCount; i++) {
                int plantX = x + random.nextInt(16);
                int plantZ = z + random.nextInt(16);
                BlockPos plantPosition = world.getHeight(new BlockPos(plantX, 0, plantZ));

                Biome biome = world.getBiome(plantPosition);

                if (biome instanceof PrehistoricaBiome) {
                    PrehistoricaBiome prehistoricaBiome = (PrehistoricaBiome) biome;
                    List<Plant> plants = prehistoricaBiome.getPlants();
                    if (plants.size() > 0) {
                        for (Plant plant : plants) {
                            if (random.nextDouble() * 100.0 <= plant.getSpawnChance()) {
                                PlantSpawner spawner = PlantRegistry.getSpawner(plant);
                                IBlockState ground = world.getBlockState(plantPosition.down());
                                IBlockState state = world.getBlockState(plantPosition);
                                IBlockState above = world.getBlockState(plantPosition.up());
                                PlantBlock block = PlantRegistry.getBlock(plant);
                                if (spawner.canSpawn(ground, state, above, block)) {
                                    spawner.spawn(world, plantPosition, block);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
