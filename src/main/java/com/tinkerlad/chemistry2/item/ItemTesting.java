package com.tinkerlad.chemistry2.item;

import com.tinkerlad.chemistry2.Chemistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 5/01/2015.
 */
public class ItemTesting extends ItemGeneric {
    public final String name = "testing";

    public ItemTesting(){
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(this,name, Chemistry.MODID);
    }
}
