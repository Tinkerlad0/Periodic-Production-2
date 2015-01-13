package com.tinkerlad.chemistry2.item;

import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.CreativeTab;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 5/01/2015.
 */
public class ItemTesting extends ItemGeneric {
    public final String name = "testing";

    public ItemTesting(){
        setUnlocalizedName(name);
        setCreativeTab(CreativeTab.tabMachines);
        GameRegistry.registerItem(this,name, Chemistry.MODID);
    }
}
