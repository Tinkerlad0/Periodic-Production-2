package com.tinkerlad.chemistry2;

import com.tinkerlad.chemistry2.block.ModBasicBlocks;
import com.tinkerlad.chemistry2.config.Config;
import com.tinkerlad.chemistry2.handler.LogHandler;
import com.tinkerlad.chemistry2.item.ModBasicItems;
import com.tinkerlad.chemistry2.proxies.CommonProxy;
import com.tinkerlad.chemistry2.registries.DynamicLocalisations;
import com.tinkerlad.chemistry2.registries.ItemRegistry;
import com.tinkerlad.chemistry2.registries.element.ElementRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import java.io.File;
import java.util.Random;


@Mod(modid = Chemistry.MODID, name = "Periodic Production 2", version = "@VERSION@")
public class Chemistry {
    public static final String MODID = "tnkchem2";

    @Mod.Instance(MODID)
    public static Chemistry instance;

    public static Random RANDOM = new Random();

    public static File mcDir;

    @SidedProxy(clientSide = "com.tinkerlad.chemistry2.proxies.ClientProxy", serverSide = "com.tinkerlad.chemistry2.proxies.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        long begin = System.currentTimeMillis();

        mcDir = event.getModConfigurationDirectory().getParentFile();

        LogHandler.init();

        Config.preInit(event.getSuggestedConfigurationFile());

        ElementRegistry.getInstance().init();
        DynamicLocalisations.getInstance();

//        DevUtils.dumpBlockNames();

        ModBasicBlocks.init();
        ModBasicItems.init();

        ItemRegistry.getInstance().generateAtomItemStacks();

//        TileEntites.init();

        long end = System.currentTimeMillis();

        LogHandler.all("Pre-Init took " + (end - begin) + " milliseconds.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();
        ItemRegistry.getInstance().realignJSONMappings(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {

    }

    @Mod.EventHandler
    public void loaded(FMLLoadCompleteEvent event) {
//        ElementAssignmentRegistry.getInstance().finalizeLoading();
        DynamicLocalisations.getInstance().registerLocalisations();
    }
}