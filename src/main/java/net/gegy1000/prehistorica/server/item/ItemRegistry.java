package net.gegy1000.prehistorica.server.item;

import net.gegy1000.prehistorica.ProjectPrehistorica;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static void register() {
        try {
            for (Field field : ItemRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);

                if (value instanceof Item) {
                    ItemRegistry.registerItem((Item) value);
                } else if (value instanceof Item[]) {
                    for (Item item : (Item[]) value) {
                        ItemRegistry.registerItem(item);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item registerItem(Item item) {
        String name = item.getUnlocalizedName().substring("item.".length());
        GameRegistry.register(item, new ResourceLocation(ProjectPrehistorica.MODID, name));
        ITEMS.add(item);
        return item;
    }
}
