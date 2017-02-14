package net.gegy1000.prehistorica.server.api.item;

import net.minecraft.item.ItemStack;

public interface ColoredItem {
    int getColor(ItemStack stack, int tintIndex);
}
