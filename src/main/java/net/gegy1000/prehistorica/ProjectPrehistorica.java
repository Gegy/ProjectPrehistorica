package net.gegy1000.prehistorica;

import net.gegy1000.prehistorica.server.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ProjectPrehistorica.MODID, name = ProjectPrehistorica.NAME, version = ProjectPrehistorica.VERSION, dependencies = "required-after:llibrary@[" + ProjectPrehistorica.LLIBRARY_VERSION + ",)")
public class ProjectPrehistorica {
    public static final String MODID = "prehistorica";
    public static final String NAME = "Project: Prehistorica";
    public static final String VERSION = "0.1.0-alpha";
    public static final String LLIBRARY_VERSION = "1.7.4";

    @SidedProxy(serverSide = "net.gegy1000.prehistorica.server.ServerProxy", clientSide = "net.gegy1000.prehistorica.client.ClientProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        ProjectPrehistorica.proxy.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        ProjectPrehistorica.proxy.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        ProjectPrehistorica.proxy.onPostInit();
    }
}
