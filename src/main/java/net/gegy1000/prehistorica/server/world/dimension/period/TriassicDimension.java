package net.gegy1000.prehistorica.server.world.dimension.period;

import net.gegy1000.prehistorica.server.api.PrehistoricDimension;
import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.gegy1000.prehistorica.server.world.provider.TriassicWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class TriassicDimension extends PrehistoricDimension {
    @Override
    public DimensionType getDimensionType() {
        return DimensionRegistry.TRIASSIC;
    }

    @Override
    public int getDefaultID() {
        return 30;
    }

    @Override
    public String getName() {
        return "prehistorica_triassic";
    }

    @Override
    public Class<? extends WorldProvider> getWorldProvider() {
        return TriassicWorldProvider.class;
    }
}
