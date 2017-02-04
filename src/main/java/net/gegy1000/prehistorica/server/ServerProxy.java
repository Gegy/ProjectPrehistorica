package net.gegy1000.prehistorica.server;

import net.gegy1000.prehistorica.server.api.plant.PlantHandler;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.item.ItemRegistry;

public class ServerProxy {
    public void onPreInit() {
        PlantHandler.register();
        BlockRegistry.register();
        ItemRegistry.register();
    }

    public void onInit() {

    }

    public void onPostInit() {

    }
}
