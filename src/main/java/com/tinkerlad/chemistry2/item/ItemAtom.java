package com.tinkerlad.chemistry2.item;

import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.CreativeTab;
import com.tinkerlad.chemistry2.registries.element.ElementRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 6/01/2015.
 */
public class ItemAtom extends ItemGeneric {

    public static final String name = "atom";

    public ItemAtom() {
        setUnlocalizedName(name);
        setCreativeTab(CreativeTab.tabMaterials);
        GameRegistry.registerItem(this, name, Chemistry.MODID);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("Z", Constants.NBT.TAG_INT)) {
            return ElementRegistry.getInstance().getColourFromZ(stack.getTagCompound().getInteger("Z"));
        }
        return 0xFFFFFF;
    }
}
