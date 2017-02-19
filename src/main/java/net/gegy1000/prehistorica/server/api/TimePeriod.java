package net.gegy1000.prehistorica.server.api;

import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.DimensionType;

public enum TimePeriod implements IStringSerializable {
    CRETACEOUS(0, "cretaceous", DimensionRegistry.CRETACEOUS),
    JURASSIC(1, "jurassic", DimensionRegistry.CRETACEOUS),
    TRIASSIC(2, "triassic", DimensionRegistry.CRETACEOUS);

    private static final TimePeriod[] META_LOOKUP = new TimePeriod[TimePeriod.values().length];

    private DimensionType dimension;
    private int metadata;
    private String name;

    TimePeriod(int metadata, String name, DimensionType dimension) {
        this.dimension = dimension;
        this.metadata = metadata;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public DimensionType getDimension() {
        return this.dimension;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public static TimePeriod from(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    static {
        for (TimePeriod period : values()) {
            META_LOOKUP[period.getMetadata()] = period;
        }
    }
}
