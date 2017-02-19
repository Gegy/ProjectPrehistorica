package net.gegy1000.prehistorica.server.world.dimension;

import net.gegy1000.prehistorica.server.api.PrehistoricDimension;
import net.gegy1000.prehistorica.server.world.dimension.period.CretaceousDimension;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DimensionRegistry {
    private static final Map<Integer, PrehistoricDimension> DIMENSIONS = new HashMap<>();

    public static final DimensionType CRETACEOUS = DimensionRegistry.create(new CretaceousDimension());

    public static void register() {
        try {
            for (Field field : DimensionRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);
                if (value instanceof DimensionType) {
                    DimensionType dimensionType = (DimensionType) value;
                    DimensionManager.registerDimension(dimensionType.getId(), dimensionType);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrehistoricDimension get(int dimension) {
        return DIMENSIONS.get(dimension);
    }

    private static DimensionType create(PrehistoricDimension dimension) {
        int id = dimension.getDefaultID();
        DimensionType type = DimensionType.register(dimension.getName(), dimension.getName(), id, dimension.getWorldProvider(), true);
        DIMENSIONS.put(id, dimension);
        return type;
    }
}
