package net.gegy1000.prehistorica.server.block;

import net.gegy1000.prehistorica.ProjectPrehistorica;
import net.gegy1000.prehistorica.server.api.item.BlockEntity;
import net.gegy1000.prehistorica.server.api.item.SubtypeBlock;
import net.gegy1000.prehistorica.server.item.SubtypeBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final PrehistoricSand PREHISTORIC_SAND = new PrehistoricSand();

    public static void register() {
        try {
            for (Field field : BlockRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);

                if (value instanceof Block) {
                    BlockRegistry.registerBlock((Block) value);
                } else if (value instanceof Block[]) {
                    for (Block block : (Block[]) value) {
                        BlockRegistry.registerBlock(block);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerBlock(Block block) {
        String name = block.getUnlocalizedName().substring("tile.".length());
        ResourceLocation identifier = new ResourceLocation(ProjectPrehistorica.MODID, name);
        GameRegistry.register(block, identifier);
        GameRegistry.register(BlockRegistry.createItemBlock(block), identifier);
        BLOCKS.add(block);

        if (block instanceof BlockEntity) {
            GameRegistry.registerTileEntity(((BlockEntity) block).getEntity(), ProjectPrehistorica.MODID + "." + name);
        }
    }

    private static ItemBlock createItemBlock(Block block) {
        if (block instanceof SubtypeBlock) {
            return new SubtypeBlockItem(block, (SubtypeBlock) block);
        }
        return new ItemBlock(block);
    }
}
