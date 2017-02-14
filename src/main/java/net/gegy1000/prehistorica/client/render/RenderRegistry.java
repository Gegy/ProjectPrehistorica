package net.gegy1000.prehistorica.client.render;

import net.gegy1000.prehistorica.ProjectPrehistorica;
import net.gegy1000.prehistorica.server.api.item.ColoredBlock;
import net.gegy1000.prehistorica.server.api.item.ColoredItem;
import net.gegy1000.prehistorica.server.api.item.DefaultRenderedItem;
import net.gegy1000.prehistorica.server.api.item.IgnoreRenderProperty;
import net.gegy1000.prehistorica.server.api.item.SubtypeRenderedItem;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRegistry {
    public void onPreInit() {
        for (Block block : BlockRegistry.BLOCKS) {
            String name = block.getUnlocalizedName().substring("tile.".length());

            if (block instanceof IgnoreRenderProperty) {
                IProperty<?>[] ignoredProperties = ((IgnoreRenderProperty) block).getIgnoredProperties();
                ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(ignoredProperties).build());
            }

            if (block instanceof DefaultRenderedItem) {
                this.registerBlockRenderer(block, ((DefaultRenderedItem) block).getResource(name), "inventory");
            } else if (block instanceof SubtypeRenderedItem) {
                SubtypeRenderedItem subtypeItem = (SubtypeRenderedItem) block;
                int[] subtypes = subtypeItem.getUsedSubtypes();
                for (int metadata : subtypes) {
                    this.registerBlockRenderer(block, metadata, subtypeItem.getResource(name, metadata), "inventory");
                }
            }
        }

        for (Item item : ItemRegistry.ITEMS) {
            String name = item.getUnlocalizedName().substring("item.".length());

            if (item instanceof DefaultRenderedItem) {
                this.registerItemRenderer(item, ((DefaultRenderedItem) item).getResource(name), "inventory");
            } else if (item instanceof SubtypeRenderedItem) {
                SubtypeRenderedItem subtypeItem = (SubtypeRenderedItem) item;
                int[] subtypes = subtypeItem.getUsedSubtypes();
                for (int metadata : subtypes) {
                    this.registerItemRenderer(item, metadata, subtypeItem.getResource(name, metadata), "inventory");
                }
            }
        }
    }

    public void onInit() {
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();

        for (Block block : BlockRegistry.BLOCKS) {
            if (block instanceof ColoredBlock) {
                ColoredBlock coloredBlock = (ColoredBlock) block;
                blockColors.registerBlockColorHandler((state, world, pos, tintIndex) -> {
                    if (pos != null) {
                        return coloredBlock.getColor(state, world, pos, tintIndex);
                    }
                    return coloredBlock.getColor(new ItemStack(block), tintIndex);
                }, block);
            }
            if (block instanceof ColoredItem) {
                ColoredItem coloredItem = (ColoredItem) block;
                itemColors.registerItemColorHandler(coloredItem::getColor, block);
            }
        }

        for (Item item : ItemRegistry.ITEMS) {
            if (item instanceof ColoredItem) {
                ColoredItem coloredItem = (ColoredItem) item;
                itemColors.registerItemColorHandler(coloredItem::getColor, item);
            }
        }
    }

    public void registerItemRenderer(Item item, String path, String type) {
        this.registerItemRenderer(item, 0, path, type);
    }

    public void registerItemRenderer(Item item, int meta, String path, String type) {
        ModelResourceLocation resource = new ModelResourceLocation(ProjectPrehistorica.MODID + ":" + path, type);
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }

    public void registerBlockRenderer(Block block, int meta, String path, String type) {
        this.registerItemRenderer(Item.getItemFromBlock(block), meta, path, type);
    }

    public void registerBlockRenderer(Block block, final String path, final String type) {
        this.registerBlockRenderer(block, 0, path, type);
    }
}
