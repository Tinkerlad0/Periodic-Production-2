package com.tinkerlad.chemistry2;

import com.tinkerlad.chemistry2.block.ModBlocks;
import com.tinkerlad.chemistry2.config.Config;
import com.tinkerlad.chemistry2.item.ModItems;
import com.tinkerlad.chemistry2.proxies.CommonProxy;
import com.tinkerlad.chemistry2.registries.ElementRegistry;
import com.tinkerlad.chemistry2.tileentities.TileEntites;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

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

        Config.preInit(event.getSuggestedConfigurationFile());

        ElementRegistry.getInstance();

        ModBlocks.init();
        ModItems.init();
        TileEntites.init();

        long end = System.currentTimeMillis();

        System.out.println("Pre-Init took " + (end - begin) + " milliseconds.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {

    }
}