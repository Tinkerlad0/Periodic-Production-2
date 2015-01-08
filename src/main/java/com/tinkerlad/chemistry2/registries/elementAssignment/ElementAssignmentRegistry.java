package com.tinkerlad.chemistry2.registries.elementAssignment;

import com.google.common.collect.ArrayListMultimap;
import com.tinkerlad.chemistry2.handler.LogHandler;
import com.tinkerlad.chemistry2.registries.element.ElementComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

/**
 * Created by brock_000 on 7/01/2015.
 */
public class ElementAssignmentRegistry {

    private static ElementAssignmentRegistry instance = new ElementAssignmentRegistry();
    List recipeList = new LinkedList();
    Map furnaceRecipes = new HashMap();
    private ArrayListMultimap<ItemStack, ElementComponent> elementComponentMap = ArrayListMultimap.create();

    public static ElementAssignmentRegistry getInstance() {
        return instance;
    }

    private static boolean invalidStack(ItemStack stack) {
        return stack == null || stack.getItem() == null || stack.stackSize < 1 || stack.getItemDamage() < 0;
    }

    public void finalizeLoading() {

        recipeList = CraftingManager.getInstance().getRecipeList();
        furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();

        ArrayListMultimap<ItemStack, ItemStack> baseItemStacks = ArrayListMultimap.create();

        List<Recipe> recipes = getAllValidRecipes();




    }

    public List<Recipe> getAllValidRecipes() {

        List<Recipe> output = new LinkedList<>();

        recipeList = CraftingManager.getInstance().getRecipeList();

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
                            Recipe unValRecipe = new Recipe(components,input);
                            if (!unValRecipe.isEmpty()){
                                output.add(unValRecipe);
                            }
                        }
                    }
                }
            }
        }
        return output;
    }
}


