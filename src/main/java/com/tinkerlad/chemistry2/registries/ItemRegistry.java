package com.tinkerlad.chemistry2.registries;

import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.item.ItemAtom;
import com.tinkerlad.chemistry2.registries.element.ElementObject;
import com.tinkerlad.chemistry2.registries.element.ElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brock_000 on 11/01/2015.
 */
public class ItemRegistry {

    private static ItemRegistry instance = new ItemRegistry();

    private Map<ElementObject, ItemAtom> itemAtomMap = new HashMap<ElementObject, ItemAtom>();

    private ItemRegistry() {

    }

    public static ItemRegistry getInstance() {
        return instance;
    }

    public void generateAtomItemStacks() {
        for (ElementObject elementObject : ElementRegistry.getInstance().getElementObjects()) {

            ItemAtom atom = new ItemAtom(elementObject);

            itemAtomMap.put(elementObject, atom);
            DynamicLocalisations.getInstance().addLocalisation(atom.getUnlocalizedName(), elementObject.getName() + " Atoms");
        }
    }

    public void realignJSONMappings(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {

            ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();


            for (ItemAtom atom : itemAtomMap.values()) {
                modelMesher.register(atom, 0, new ModelResourceLocation(Chemistry.MODID + ":atom", "inventory"));
                ModelBakery.addVariantName(atom, atom.element.getSymbol());
            }
        }
    }

    public ItemAtom getAtomFromElement(ElementObject elementObject) {
        return itemAtomMap.get(elementObject);
    }
}
