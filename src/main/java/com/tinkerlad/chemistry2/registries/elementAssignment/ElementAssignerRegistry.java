package com.tinkerlad.chemistry2.registries.elementAssignment;

import com.google.common.collect.ArrayListMultimap;
import com.tinkerlad.chemistry2.registries.element.ElementComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.*;

/**
 * Created by brock_000 on 7/01/2015.
 */
public class ElementAssignerRegistry {

    private static ElementAssignerRegistry instance = new ElementAssignerRegistry();
    List recipeList = new LinkedList();
    Map furnaceRecipes = new HashMap();
    private ArrayListMultimap<ItemStack, ElementComponent> elementComponentMap = ArrayListMultimap.create();

    public static ElementAssignerRegistry getInstance() {
        return instance;
    }

    public void postInit() {

        List<ItemStack> items = getAllBlocksAndItems();
        recipeList = CraftingManager.getInstance().getRecipeList();
        furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();
        System.out.println("Initial Item = " + items.get(400));
        System.out.println(getBaseItemsViaRecipe(items.get(400)));


    }

    private List<ItemStack> getBaseItemsViaRecipe(ItemStack stack) {
        List<ItemStack> output = new LinkedList<>();

        Queue<ItemStack> openList = new LinkedList<>();
        openList.add(stack);

        LinkedList<ItemStack> closedList = new LinkedList<>();
        List<ItemStack> baseList = new LinkedList<>();

        //First check crafting recipes

        int depth = 0;

        while (!openList.isEmpty()) {
            ItemStack currentItemStack = openList.poll();

            depth++;

            System.out.println("depth = " + depth);

            boolean isBaseItem = true;

            for (Object object : recipeList) {
                if (object instanceof ShapedRecipes) {
                    System.out.println("Recipe Output = " + ((ShapedRecipes) object).getRecipeOutput().toString());
                    if (((ShapedRecipes) object).getRecipeOutput().getItem().equals(currentItemStack.getItem())) {
                        //This is a recipe we want
                        isBaseItem = false;
                        System.out.println("Found Shaped Recipe for " + currentItemStack.getItem());
                        for (ItemStack recipeStack : ((ShapedRecipes) object).recipeItems) {
                            openList.add(recipeStack);
                        }
                    }
                } else if (object instanceof ShapelessRecipes) {
                    System.out.println("Recipe Output = " + ((ShapelessRecipes) object).getRecipeOutput().toString());
                    if (((ShapelessRecipes) object).getRecipeOutput().getItem().equals(currentItemStack.getItem())) {
                        //This is a recipe we want
                        System.out.println("Found Shapeless Recipe for " + currentItemStack.getItem());
                        isBaseItem = false;
                        for (Object obj : ((ShapelessRecipes) object).recipeItems) {
                            if (obj instanceof ItemStack) {
                                openList.add((ItemStack) obj);
                            }

                        }
                    }
                }
            }

            //Now Check furnace Recipes

            Iterator furnaceIterator = furnaceRecipes.values().iterator();

            while (furnaceIterator.hasNext()) {
                Object smelted = furnaceIterator.next();
                if (smelted instanceof ItemStack) {
                    ItemStack smeltedStack = (ItemStack) smelted;
                    if (smeltedStack.getItem().equals(currentItemStack)) {
                        System.out.println("Found Furnace Recipe for " + currentItemStack.getItem());
                        isBaseItem = false;
                        openList.add((ItemStack) furnaceRecipes.get(smelted));
                    }
                }
            }

            //Finished Recipe Searching
            System.out.println("Added " + currentItemStack + " to the closed list");
            closedList.add(currentItemStack);

            if (isBaseItem) {
                System.out.println("Added " + currentItemStack + " to the base list");
                baseList.add(currentItemStack);
            }

            return baseList;
        }
        return baseList;
    }


    private List<ItemStack> getAllBlocksAndItems() {
        Iterator<Item> itemIterator = Item.itemRegistry.iterator();
        Iterator<Block> blockIterator = Block.blockRegistry.iterator();

        List<ItemStack> stacks = new LinkedList<>();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            stacks.add(new ItemStack(item));
        }

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            stacks.add(new ItemStack(block));
        }

        return stacks;
    }
}
