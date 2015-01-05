package com.tinkerlad.chemistry2.registries;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.tinkerlad.chemistry2.util.ElementObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by brock_000 on 4/01/2015.
 */
public class ElementRegistry {

    private static final ElementRegistry instance = new ElementRegistry();

    private Set<ElementObject> elementObjects;

    public static ElementRegistry getInstance(){
        return instance;
    }

    private ElementRegistry(){

        elementObjects = new HashSet<ElementObject>();

        Set<Map.Entry<String, JsonElement>> elementRaw = getElementsJson();
        Gson gson = new Gson();

        for (Map.Entry<String, JsonElement> entry : elementRaw){
            ElementObject element = gson.fromJson(entry.getValue(),ElementObject.class);
            System.out.println("Element " + entry.getKey() + " stored with data {" + element + "}");

            if(Integer.parseInt(entry.getKey()) != Integer.parseInt(element.getZ())){
                System.err.println("ERROR!! Check Element " + element + " registered as " + entry.getKey());
            }

        }
    }

    private static final String jsonLocation = "/assets/tnkchem2/data/elements.json";

    private static Set<Map.Entry<String, JsonElement>> getElementsJson() {
        InputStream is = ElementRegistry.class.getResourceAsStream(jsonLocation);
        InputStreamReader isr = new InputStreamReader(is);

        JsonReader jReader = new JsonReader(isr);
        jReader.setLenient(true);

        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(jReader);
        JsonObject lst = element.getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = lst.entrySet();

        return entries;
    }
}
