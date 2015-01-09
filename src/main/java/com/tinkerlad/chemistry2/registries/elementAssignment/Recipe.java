package com.tinkerlad.chemistry2.registries.elementAssignment;

import net.minecraft.item.ItemStack;

/**
 * Created by brock_000 on 8/01/2015.
 */
public class Recipe {

    private ItemStack output;
    private ItemStack[] recipeItems;

    public Recipe(ItemStack[] recipeItems, ItemStack output) {
        this.recipeItems = recipeItems;
        this.output = output;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    public ItemStack[] getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(ItemStack[] recipeItems) {
        this.recipeItems = recipeItems;
    }

    @Override
    public String toString() {
        String recipe = "";
        for (ItemStack stack : recipeItems) {
            if (stack == null || stack.getItem() == null) continue;
            recipe += stack.toString() + ",";
        }
        return "{Recipe for " + output + " contains: " + recipe;
    }

    public boolean isEmpty() {
        return recipeItems.length < 1;
    }


}
