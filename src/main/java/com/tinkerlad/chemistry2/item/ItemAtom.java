package com.tinkerlad.chemistry2.item;

import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.registries.element.ElementObject;
import com.tinkerlad.chemistry2.registries.element.ElementRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 6/01/2015.
 */
public class ItemAtom extends ItemGeneric {

    public ElementObject element;

    public ItemAtom(ElementObject elementObject) {
        element = elementObject;
        setUnlocalizedName(element.getSymbol());
        setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(this, element.getSymbol(), Chemistry.MODID);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        return ElementRegistry.getInstance().getColourFromZ(Integer.parseInt(element.getZ()));
    }
}
