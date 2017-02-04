package net.gegy1000.prehistorica.server.api.item;

import net.minecraft.tileentity.TileEntity;

public interface BlockEntity {
    Class<? extends TileEntity> getEntity();
}
