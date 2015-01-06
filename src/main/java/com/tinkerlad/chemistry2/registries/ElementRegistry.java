package com.tinkerlad.chemistry2.registries;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.tinkerlad.chemistry2.handler.LogHandler;

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
    private static final String jsonLocation = "/assets/tnkchem2/data/elements.json";
    private Set<ElementObject> elementObjects;

    private ElementRegistry() {

        elementObjects = new HashSet<ElementObject>();

        Set<Map.Entry<String, JsonElement>> elementRaw = getElementsJson();
        Gson gson = new Gson();

        for (Map.Entry<String, JsonElement> entry : elementRaw) {
            ElementObject element = gson.fromJson(entry.getValue(), ElementObject.class);
            LogHandler.all("Element " + entry.getKey() + " stored with data {" + element + "}");

            if (Integer.parseInt(entry.getKey()) != Integer.parseInt(element.getZ())) {
                LogHandler.error("ERROR!! Check Element " + element + " registered as " + entry.getKey());
                LogHandler.error(element.getName() + " has not been added ElementRegistry to avoid conflicts");
                LogHandler.error("Please contact the mod author to resolve this issue");
            } else {
                elementObjects.add(element);
            }
        }
    }

    public static ElementRegistry getInstance(){
        return instance;
    }

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

    public ElementObject getElementFromZ(int Z) {
        for (ElementObject obj : elementObjects) {
            if (obj.getZ().matches(String.valueOf(Z))) {
                return obj;
            }
        }
        return null;
    }

    public ElementObject getElementFromSymbol(String symbol) {
        for (ElementObject obj : elementObjects) {
            if (obj.getSymbol().equals(symbol)) {
                return obj;
            }
        }
        return null;
    }
}
