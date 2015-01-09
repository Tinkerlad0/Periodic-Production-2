package com.tinkerlad.chemistry2.registries.element;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.tinkerlad.chemistry2.handler.LogHandler;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.*;

/**
 * Created by brock_000 on 4/01/2015.
 */
public class ElementRegistry {

    private static final ElementRegistry instance = new ElementRegistry();
    private static final String elementsJsonLoc = "/assets/tnkchem2/data/elements.json";
    private Set<ElementObject> elementObjects;

    private ArrayListMultimap<ItemStack, ElementComponent> elementMappings = ArrayListMultimap.create();

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

    public static ElementRegistry getInstance() {
        return instance;
    }

    private static Set<Map.Entry<String, JsonElement>> getElementsJson() {
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

    public void configureElementMappings() {


    }

    public List<ElementComponent> getElementsFromCompoundString(String compound) {
        StringReader reader = new StringReader(compound);

        StringBuilder elementTemp = new StringBuilder();
        StringBuilder quantityTemp = new StringBuilder();

        List<ElementComponent> output = new LinkedList<ElementComponent>();

        try {
            char charTemp = (char) reader.read();

            if (!(getCharType(charTemp) == 3)) {
                throw new InvalidObjectException("String Is not a valid compound");
            }

            elementTemp.append(charTemp);

            int c;

            while ((c = reader.read()) != -1) {
                charTemp = (char) c;
                if (getCharType(charTemp) == 2) {
                    elementTemp.append(charTemp);
                } else if (getCharType(charTemp) == 1) {
                    quantityTemp.append(charTemp);
                } else if (getCharType(charTemp) == 3) {
                    //Element Complete


                    int quantity = 1;
                    if (!quantityTemp.equals("")) {
                        quantity = Integer.valueOf(quantityTemp.toString());
                    }

                    ElementObject elementObject = getElementFromSymbol(elementTemp.toString());

                    ElementComponent element = new ElementComponent(elementObject, quantity);

                    output.add(element);

                    elementTemp = new StringBuilder();
                    quantityTemp = new StringBuilder();

                    elementTemp.append(charTemp);
                } else {
                    throw new InvalidObjectException("Unrecognised Character in compound: " + charTemp);
                }
            }

            int quantity = 1;
            if (!quantityTemp.equals("")) {
                quantity = Integer.valueOf(quantityTemp.toString());
            }

            ElementObject elementObject = getElementFromSymbol(elementTemp.toString());

            ElementComponent element = new ElementComponent(elementObject, quantity);

            output.add(element);

            reader.close();

        } catch (InvalidObjectException e) {
            System.err.println(e.getMessage());
            return output;
        } catch (IOException e) {
            e.printStackTrace();
            return output;
        }

        return output;
    }

}
