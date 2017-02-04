package net.gegy1000.prehistorica.server.api.item;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;

public interface SubtypeBlock extends SubtypeRenderedItem {
    String getSubtypeName(IBlockState state);

    @Override
    default int[] getUsedSubtypes() {
        Block block = (Block) this;
        int[] subtypes = new int[BlockSand.EnumType.values().length * TimePeriod.values().length];
        int index = 0;
        for (IBlockState state : block.getBlockState().getValidStates()) {
            subtypes[index++] = block.damageDropped(state);
        }
        return subtypes;
    }

    @Override
    default String getResource(String name, int metadata) {
        Block block = (Block) this;
        IBlockState state = block.getStateFromMeta(metadata);
        return this.getSubtypeName(state);
    }
}
