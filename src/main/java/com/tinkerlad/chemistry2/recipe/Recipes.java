package com.tinkerlad.chemistry2.recipe;

import com.tinkerlad.chemistry2.item.ModBasicItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 17/01/2015.
 */
public class Recipes {
    public static void init() {

        ItemStack hydrogen = new ItemStack(ModBasicItems.itemAtom);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("Z", 1);
        hydrogen.setTagCompound(tagCompound);

        GameRegistry.addRecipe(hydrogen, "X", 'X', Items.feather);

        for (int i = 2; i < 95; i++) {
            ItemStack output = new ItemStack(ModBasicItems.itemAtom);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("Z", i);
            output.setTagCompound(tag);
            System.out.println(i + 1);

            ItemStack input = new ItemStack(ModBasicItems.itemAtom);
            NBTTagCompound tag2 = new NBTTagCompound();
            tag2.setInteger("Z", i - 1);
            output.setTagCompound(tag2);

            GameRegistry.addShapelessRecipe(output, input, Items.feather);
        }
    }
}
