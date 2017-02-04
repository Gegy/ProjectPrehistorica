package net.gegy1000.prehistorica.server.api.plant;

import net.gegy1000.prehistorica.server.block.plant.DoublePlantBlock;
import net.gegy1000.prehistorica.server.block.plant.PlantBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlantSpawner {
    PlantSpawner SINGLE_GROUND = new PlantSpawner() {
        @Override
        public void spawn(World world, BlockPos pos, PlantBlock block) {
            world.setBlockState(pos, block.getDefaultState(), 2);
        }

        @Override
        public boolean canSpawn(IBlockState ground, IBlockState state, IBlockState above, PlantBlock block) {
            return (ground.getMaterial() == Material.GROUND || ground.getMaterial() == Material.GRASS) && canPlaceAir(state, block) && canPlaceAir(above, block);
        }
    };
    PlantSpawner DOUBLE_GROUND = new PlantSpawner() {
        @Override
        public void spawn(World world, BlockPos pos, PlantBlock block) {
            IBlockState state = block.getDefaultState();
            world.setBlockState(pos, state.withProperty(DoublePlantBlock.HALF, DoublePlantBlock.BlockHalf.LOWER), 2);
            world.setBlockState(pos.up(), state.withProperty(DoublePlantBlock.HALF, DoublePlantBlock.BlockHalf.UPPER), 2);
        }

        @Override
        public boolean canSpawn(IBlockState ground, IBlockState state, IBlockState above, PlantBlock block) {
            return (ground.getMaterial() == Material.GROUND || ground.getMaterial() == Material.GRASS) && canPlaceAir(state, block) && canPlaceAir(above, block);
        }
    };
    PlantSpawner SINGLE_BEACH = new PlantSpawner() {
        @Override
        public void spawn(World world, BlockPos pos, PlantBlock block) {
            world.setBlockState(pos, block.getDefaultState(), 2);
        }

        @Override
        public boolean canSpawn(IBlockState ground, IBlockState state, IBlockState above, PlantBlock block) {
            return ground.getMaterial() == Material.SAND && canPlaceAir(state, block) && canPlaceAir(above, block);
        }
    };

    void spawn(World world, BlockPos pos, PlantBlock block);

    boolean canSpawn(IBlockState ground, IBlockState state, IBlockState above, PlantBlock block);

    static boolean canPlaceAir(IBlockState state, PlantBlock block) {
        Material material = state.getMaterial();
        return (material.isReplaceable() && !material.isLiquid()) || state.getBlock() == block;
    }
}
