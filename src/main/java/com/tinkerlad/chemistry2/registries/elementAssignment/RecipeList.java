package com.tinkerlad.chemistry2.registries.elementAssignment;

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
                return recipe;
            }
        }
        return null;
    }

    public boolean addRecipe(Recipe recipe) {
        return recipeList.add(recipe);
    }

    public RecipeList copy() {
        return new RecipeList(recipeList);
    }
}
