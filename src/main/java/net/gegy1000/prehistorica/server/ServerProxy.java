package net.gegy1000.prehistorica.server;

import net.gegy1000.prehistorica.server.api.plant.PlantHandler;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.command.TimeTravelCommand;
import net.gegy1000.prehistorica.server.item.ItemRegistry;
import net.gegy1000.prehistorica.server.world.biome.BiomeRegistry;
import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ServerProxy {
    public void onPreInit() {
        PlantHandler.register();
        BlockRegistry.register();
        ItemRegistry.register();
        BiomeRegistry.register();
        DimensionRegistry.register();
    }

    public void onInit() {

    }

    public void onPostInit() {

    }

    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new TimeTravelCommand());
    }
}
