package com.tinkerlad.chemistry2.registries;

import net.minecraftforge.fml.common.registry.LanguageRegistry;

import java.util.HashMap;

/**
 * Created by brock_000 on 12/01/2015.
 */
public class DynamicLocalisations {

    private static DynamicLocalisations instance = new DynamicLocalisations();
    private HashMap<String, String> localisations = new HashMap<String, String>();

    private DynamicLocalisations() {

    }

    public static DynamicLocalisations getInstance() {
        return instance;
    }

    public void registerLocalisations() {
        LanguageRegistry.instance().injectLanguage("en_US", localisations);
    }

    public void addLocalisation(String itemName, String localisation) {
        localisations.put(itemName + ".name",
                localisation);
    }
}
