package com.tinkerlad.chemistry2.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGeneric extends Block {
    protected BlockGeneric(Material material) {
        super(material);
        this.setUnlocalizedName("default");
    }
}
