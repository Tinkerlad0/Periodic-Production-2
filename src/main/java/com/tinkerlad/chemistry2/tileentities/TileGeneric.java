package com.tinkerlad.chemistry2.tileentities;

import net.minecraft.tileentity.TileEntity;

public class TileGeneric extends TileEntity {
    public void markForUpdate() {
        worldObj.markBlockForUpdate(getPos());
    }
}
