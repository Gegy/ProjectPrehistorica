package net.gegy1000.prehistorica.server.tab;

import net.gegy1000.prehistorica.ProjectPrehistorica;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class TabRegistry {
    public static final CreativeTabs BLOCKS = new CreativeTabs(ProjectPrehistorica.MODID + ".blocks") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.GRASS);
        }
    };

    public static final CreativeTabs ITEMS = new CreativeTabs(ProjectPrehistorica.MODID + ".items") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.GRASS);
        }
    };
}
