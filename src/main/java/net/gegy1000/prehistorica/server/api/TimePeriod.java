package net.gegy1000.prehistorica.server.api;

import net.minecraft.util.IStringSerializable;

public enum TimePeriod implements IStringSerializable {
    CRETACEOUS(0, "cretaceous"),
    JURASSIC(1, "jurassic"),
    TRIASSIC(2, "triassic");

    private static final TimePeriod[] META_LOOKUP = new TimePeriod[TimePeriod.values().length];

    private int metadata;
    private String name;

    TimePeriod(int metadata, String name) {
        this.metadata = metadata;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
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
