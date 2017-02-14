package net.gegy1000.prehistorica.client;

import net.gegy1000.prehistorica.client.render.RenderRegistry;
import net.gegy1000.prehistorica.server.ServerProxy;

public class ClientProxy extends ServerProxy {
    public static final RenderRegistry RENDER_REGISTRY = new RenderRegistry();

    @Override
    public void onPreInit() {
        super.onPreInit();

        RENDER_REGISTRY.onPreInit();
    }

    @Override
    public void onInit() {
        super.onInit();

        RENDER_REGISTRY.onInit();
    }

    @Override
    public void onPostInit() {
        super.onPostInit();
    }
}
