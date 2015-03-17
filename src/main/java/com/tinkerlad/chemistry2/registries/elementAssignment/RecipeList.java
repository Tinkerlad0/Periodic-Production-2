package com.tinkerlad.chemistry2.registries.elementAssignment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by brock_000 on 8/01/2015.
 */
public class RecipeList {
    private List<Recipe> recipeList;

    public RecipeList(List<Recipe> recipes) {
        recipeList = recipes;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public boolean contains(Recipe recipe) {
        return recipeList.contains(recipe);
    }

    public Recipe getRecipeFromStack(ItemStack stack) {
        for (Recipe recipe : recipeList) {
            if (stack == null || recipe == null || recipe.getOutput() == null) continue;
            if (recipe.getOutput().getItem() == stack.getItem()) {

                boolean blockRecipe = false;
                for (ItemStack rec : recipe.getRecipeItems()) {
                    System.out.println("rec = " + Item.itemRegistry.getNameForObject(rec.getItem()));
                    System.out.println("test = " + ((Item.itemRegistry.getNameForObject(rec.getItem())).toString().contains("block")));
                    if ((Item.itemRegistry.getNameForObject(rec.getItem())).toString().contains("block")) {
                        String itemSub = (Item.itemRegistry.getNameForObject(stack.getItem())).toString();
                        String item = Item.itemRegistry.getNameForObject(stack.getItem()).toString();

                        String itemTypeName = item.substring(item.lastIndexOf(':'), item.lastIndexOf('_'));

                        if (itemSub.contains(itemTypeName)) blockRecipe = true;
                    }
                }

                if (blockRecipe) recipe.setValid(false);
                return recipe;
            }
        }
        return null;
    }

    public boolean addRecipe(Recipe recipe) {
        return recipeList.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {

        for (Recipe base : recipeList) {
            if (base.matches(recipe)) {
                recipeList.remove(base);
            }
        }

    }

    public RecipeList copy() {
        return new RecipeList(recipeList);
    }
}
