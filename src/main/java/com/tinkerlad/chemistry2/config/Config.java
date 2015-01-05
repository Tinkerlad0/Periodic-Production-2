package com.tinkerlad.chemistry2.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    private static Configuration config;

    public static void preInit(File configurationFile) {
        config = new Configuration(configurationFile);
        loadConfig();
    }

    public static void saveConfig() {
        if (config != null) {
            config.save();
        }
    }

    public static void loadConfig() {
        if (config != null) {
            config.load();

            saveConfig();
        }
    }

}
