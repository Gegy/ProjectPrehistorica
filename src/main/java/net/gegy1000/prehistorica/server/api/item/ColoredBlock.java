package net.gegy1000.prehistorica.server.api.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ColoredBlock extends ColoredItem {
    int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex);
}
