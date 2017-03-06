package net.gegy1000.prehistorica.server;

import net.gegy1000.prehistorica.server.api.plant.PlantRegistry;
import net.gegy1000.prehistorica.server.block.BlockRegistry;
import net.gegy1000.prehistorica.server.command.TimeTravelCommand;
import net.gegy1000.prehistorica.server.item.ItemRegistry;
import net.gegy1000.prehistorica.server.world.biome.BiomeRegistry;
import net.gegy1000.prehistorica.server.world.biome.PrehistoricaDecorator;
import net.gegy1000.prehistorica.server.world.dimension.DimensionRegistry;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy {
    public void onPreInit() {
        PlantRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        BiomeRegistry.register();
        DimensionRegistry.register();

        GameRegistry.registerWorldGenerator(new PrehistoricaDecorator(), 0);
    }

    public void onInit() {

    }

    public void onPostInit() {

    }

    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new TimeTravelCommand());
    }
}
