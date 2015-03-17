package com.tinkerlad.chemistry2.block;

import com.tinkerlad.chemistry2.CreativeTab;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by brock_000 on 17/01/2015.
 */
public class BlockTesting extends BlockGeneric {

    private final String name = "testingBlock";

    public BlockTesting() {
        super(Material.rock);
        setCreativeTab(CreativeTab.tabMachines);
        setUnlocalizedName(name);
        GameRegistry.registerBlock(this, name);
    }
}
