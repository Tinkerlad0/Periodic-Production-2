package com.tinkerlad.chemistry2.registries.elementAssignment;

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
        for (Recipe obj : recipeList) {
            if (recipe.getOutput() == obj.getOutput() && recipe.getRecipeItems() == obj.getRecipeItems()) {
                return true;
            }
        }

        return false;
    }
}
