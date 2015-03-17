package com.tinkerlad.chemistry2.registries.elementAssignment;

import com.google.common.collect.ArrayListMultimap;
import com.tinkerlad.chemistry2.handler.LogHandler;
import com.tinkerlad.chemistry2.registries.element.ElementComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by brock_000 on 7/01/2015.
 */
public class ElementAssignmentRegistry {

    private static ElementAssignmentRegistry instance = new ElementAssignmentRegistry();
    List recipeList = new LinkedList();
    Map furnaceRecipes = new HashMap();
    private ArrayListMultimap<ItemStack, ElementComponent> elementComponentMap = ArrayListMultimap.create();
    private ArrayListMultimap<ItemStack, ItemStack> itemBaseItemMap = ArrayListMultimap.create();

    public static ElementAssignmentRegistry getInstance() {
        return instance;
    }

    private static boolean invalidStack(ItemStack stack) {
        return stack == null || stack.getItem() == null || stack.stackSize < 1 || stack.getItemDamage() < 0;
    }

    public void assignElements() {

        LogHandler.warn("Element Assignment Registry is in loading phase");

        recipeList = CraftingManager.getInstance().getRecipeList();
        furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();

        LogHandler.warn("ElementAssignmentRegistry got vanilla recipes");

        RecipeList recipes = new RecipeList(getAllValidRecipes());

        LogHandler.warn("ElementAssignmentRegistry compiled recipe list");

        loadBaseItemsMap(recipes);

        assignElementsToBaseItems();

    }

    private void assignElementsToBaseItems() {
        List<ItemStack> baseStacks = new ArrayList<>();


        for (ItemStack stack : itemBaseItemMap.keySet()) {
            List<ItemStack> stacks = itemBaseItemMap.get(stack);

            for (ItemStack itemStack : stacks) {
                if (itemStack == null) continue;
                baseStacks.add(itemStack);
            }
        }


        try {
            File log = new File("C:\\temp\\PP2Dump.txt");
            log.delete();
            log.createNewFile();
            FileWriter aWriter = new FileWriter(log, true);
            aWriter.write("Beginning Periodic Production 2 Temp Log File: -->" + System.lineSeparator());

            for (ItemStack stack : baseStacks) {
                aWriter.write(Item.itemRegistry.getNameForObject(stack.getItem()) + "," + stack.getDisplayName() + System.lineSeparator());
            }

            aWriter.flush();
            aWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ENDING");
    }

    private void loadBaseItemsMap(RecipeList recipes) {
        RecipeList recipeLstTemp = recipes.copy();

        ArrayList<Recipe> baseRecipesList = new ArrayList<>();

        for (Recipe recipe : recipeLstTemp.getRecipeList()) {
            ArrayList<ItemStack> baseItems = getRecipeBaseItems(recipe.getOutput(), recipeLstTemp);

            itemBaseItemMap.putAll(recipe.getOutput(), baseItems);

        }


        //Just some debug stuff.
        try {
            File log = new File("C:\\temp\\PP2ItemBases.csv");
            log.delete();
            log.createNewFile();
            FileWriter aWriter = new FileWriter(log, true);
            aWriter.write("Beginning Periodic Production 2 Temp Dump File: -->" + System.lineSeparator());

            List<Item> baseItems = new ArrayList<>();

            for (ItemStack stack : itemBaseItemMap.keySet()) {

                for (ItemStack component : itemBaseItemMap.get(stack)) {

                    if (component == null) continue;

                    if (!baseItems.contains(component.getItem())) {
                        baseItems.add(component.getItem());
                    }


                }
            }

            for (Item item : baseItems) {
                aWriter.write(Item.itemRegistry.getNameForObject(item) + System.lineSeparator());
            }

            aWriter.flush();
            aWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<ItemStack> getRecipeBaseItems(ItemStack stack, RecipeList recipes) {

        //BBFS I think xD

        ArrayList<ItemStack> baseList = new ArrayList<>();

        Queue<ItemStack> openList = new LinkedList<>();
        openList.add(stack);

        int depth = 0;

        while (!openList.isEmpty() && depth <= 500) {
            depth++;
            ItemStack current = openList.remove();

            updateRecipeListAndBaseItemList(current, recipes, recipes.getRecipeFromStack(current), baseList, openList);
        }
        return baseList;
    }

    private void updateRecipeListAndBaseItemList(ItemStack current, RecipeList recipes, Recipe recipe, ArrayList<ItemStack> baseList, Queue<ItemStack> openList) {

        if (recipe == null) {
            baseList.add(current);
        } else if (recipe.isValid()) {
            openList.addAll(Arrays.asList(recipe.getRecipeItems()));
        } else {
            recipes.removeRecipe(recipe);
            updateRecipeListAndBaseItemList(current, recipes, recipes.getRecipeFromStack(current), baseList, openList);
        }
    }

    public List<Recipe> getAllValidRecipes() {

        List<Recipe> output = new LinkedList<>();

        recipeList = CraftingManager.getInstance().getRecipeList();
        Map<ItemStack, ItemStack> smelting = FurnaceRecipes.instance().getSmeltingList();


        for (Object recipe : recipeList) {
            if (recipe instanceof IRecipe) {
                ItemStack input = ((IRecipe) recipe).getRecipeOutput();
                if (!invalidStack(input)) {
                    LogHandler.debug("Adding recipe for " + input.toString());
                    ItemStack[] components = null;

                    if (recipe instanceof ShapelessOreRecipe && ((ShapelessOreRecipe) recipe).getInput().size() > 0) {
                        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                        for (Object o : ((ShapelessOreRecipe) recipe).getInput()) {
                            if (o instanceof ItemStack) {
                                inputs.add((ItemStack) o);
                            }
                        }
                        components = inputs.toArray(new ItemStack[inputs.size()]);
                    } else if (recipe instanceof ShapedOreRecipe) {
                        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                        for (Object o : ((ShapedOreRecipe) recipe).getInput()) {

                            if (o instanceof ItemStack) {
                                inputs.add((ItemStack) o);
                            } else if (o instanceof String) {
                                inputs.add(OreDictionary.getOres((String) o).get(0));
                            } else if (o instanceof ArrayList && !((ArrayList) o).isEmpty()) {
                                inputs.add((ItemStack) ((ArrayList) o).get(0));
                            }
                        }
                        components = inputs.toArray(new ItemStack[inputs.size()]);

                    } else if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[]) {
                        components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
                    } else if (recipe instanceof ShapedRecipes) {
                        components = ((ShapedRecipes) recipe).recipeItems;
                    }


                    if (components != null) {
                        boolean badRecipe = false;
                        for (ItemStack component : components) {
                            if (component != null && (component.getItem() == null || component.isItemEqual(input) || component.stackSize < 1)) {
                                badRecipe = true;
                            }
                        }
                        if (!badRecipe) {
                            Recipe unValRecipe = new Recipe(components, input);
                            if (!unValRecipe.isEmpty()) {
                                output.add(unValRecipe);
                            }
                        }
                    }
                }
            }
        }

        for (ItemStack raw : smelting.keySet()) {
            ItemStack smelted = smelting.get(raw);

            if (invalidStack(raw) || invalidStack(smelted)) {
                continue;
            }

            ItemStack[] component = {raw};

            if (raw != null && (raw.getItem() == null || raw.isItemEqual(smelted) || raw.stackSize < 1)) {
                continue;
            }

            Recipe smeltingRecipe = new Recipe(component, smelted);

            output.add(smeltingRecipe);
        }

        return output;
    }
}


