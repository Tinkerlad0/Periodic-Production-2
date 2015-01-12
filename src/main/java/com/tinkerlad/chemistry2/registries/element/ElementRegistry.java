package com.tinkerlad.chemistry2.registries.element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.tinkerlad.chemistry2.handler.LogHandler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by brock_000 on 4/01/2015.
 */
public class ElementRegistry {

    private static final ElementRegistry instance = new ElementRegistry();
    private static final String elementsJsonLoc = "/assets/tnkchem2/data/element.json";
    private Set<ElementObject> elementObjects;

    private Map<Integer, Integer> elementColours = new HashMap<Integer, Integer>();

    private ElementRegistry() {
    }

    public static ElementRegistry getInstance() {
        return instance;
    }

    private static Set<Map.Entry<String, JsonElement>> loadElementsJson() {
        InputStream is = ElementRegistry.class.getResourceAsStream(elementsJsonLoc);
        InputStreamReader isr = new InputStreamReader(is);

        JsonReader jReader = new JsonReader(isr);
        jReader.setLenient(true);

        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(jReader);
        JsonObject lst = element.getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = lst.entrySet();

        return entries;
    }

    /**
     * @param c Character to test
     * @return returns 1 for number 2 for lowercase, 3 for uppercase, -1 indicates non alpha numeric
     */
    private static int getCharType(char c) {
        if (c >= '0' && c <= '9') {
//            System.out.println(c + " is considered a number");
            return 1;
        }
        if (c >= 'a' && c <= 'z') {
//            System.out.println(c + " is considered lowercase");
            return 2;
        }
        if (c >= 'A' && c <= 'Z') {
//            System.out.println(c + " is considered uppercase");
            return 3;
        }
//        System.out.println(c + " is considered invalid");
        return -1;
    }

    public void init() {
        elementObjects = new HashSet<ElementObject>();

        Set<Map.Entry<String, JsonElement>> elementRaw = loadElementsJson();
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

        //Load element colour information from element_colour.json

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

    public Set<ElementObject> getElementObjects() {
        return elementObjects;
    }

    public int getColourFromZ(int Z) {
        return 0xFF0000; //elementColours.get(Z);
    }
}
