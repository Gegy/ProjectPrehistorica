package net.gegy1000.prehistorica.server.item;

import net.gegy1000.prehistorica.server.api.item.SubtypeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SubtypeBlockItem extends ItemBlock {
    private SubtypeBlock subtype;

    public SubtypeBlockItem(Block block, SubtypeBlock subtype) {
        super(block);
        this.subtype = subtype;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        IBlockState state = this.block.getStateFromMeta(stack.getItemDamage());
        return "tile." + this.subtype.getSubtypeName(state);
    }
}
