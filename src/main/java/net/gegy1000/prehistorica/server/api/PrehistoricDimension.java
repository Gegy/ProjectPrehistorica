package net.gegy1000.prehistorica.server.api;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public abstract class PrehistoricDimension {
    public abstract DimensionType getDimensionType();

    public abstract int getDefaultID();

    public abstract String getName();

    public abstract Class<? extends WorldProvider> getWorldProvider();
}
