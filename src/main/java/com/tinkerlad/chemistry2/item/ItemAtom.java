package com.tinkerlad.chemistry2.item;

import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.CreativeTab;
import com.tinkerlad.chemistry2.registries.element.ElementObject;
import com.tinkerlad.chemistry2.registries.element.ElementRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.lwjgl.input.Keyboard;

import java.util.List;

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
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("Z", Constants.NBT.TAG_INT)) {
                tooltip.add("Z: " + stack.getTagCompound().getInteger("Z"));
                tooltip.add("Name: " + ElementRegistry.getInstance().getElementFromZ(stack.getTagCompound().getInteger("Z")).getName());
            }
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("Z", Constants.NBT.TAG_INT)) {
            return ElementRegistry.getInstance().getColourFromZ(stack.getTagCompound().getInteger("Z"));
        }
        return 0xFFFFFF;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {

        String name = "Corrupted Element!";
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Z", Constants.NBT.TAG_INT)) {
            int z = stack.getTagCompound().getInteger("Z");
            ElementObject object = ElementRegistry.getInstance().getElementFromZ(z);

            if (!(object == null)) {
                name = object.getName();
            }
        }

        return name;
    }
}