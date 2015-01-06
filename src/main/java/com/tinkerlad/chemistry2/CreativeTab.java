package com.tinkerlad.chemistry2;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by brock_000 on 6/01/2015.
 */
public class CreativeTab {

    public static CreativeTabs tabMaterials = new CreativeTabs("tabMaterial") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Items.redstone;
        }
    };

    //Use a custom item as an icon (assuming it is instantiated in a class called ModItems)
    public static CreativeTabs tabMachines = new CreativeTabs("tabMachines") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.lit_furnace);
        }
    };
}
