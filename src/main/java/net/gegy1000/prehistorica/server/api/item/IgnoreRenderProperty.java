package net.gegy1000.prehistorica.server.api.item;

import net.minecraft.block.properties.IProperty;

public interface IgnoreRenderProperty {
    IProperty<?>[] getIgnoredProperties();
}
