package net.gegy1000.prehistorica.server.api.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public interface SubtypeBlock extends SubtypeRenderedItem {
    String getSubtypeName(IBlockState state);

    @Override
    default int[] getUsedSubtypes() {
        Block block = (Block) this;
        ImmutableList<IBlockState> states = block.getBlockState().getValidStates();
        int[] subtypes = new int[states.size()];
        int index = 0;
        for (IBlockState state : states) {
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
