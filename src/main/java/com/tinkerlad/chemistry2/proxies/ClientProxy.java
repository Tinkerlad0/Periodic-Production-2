package com.tinkerlad.chemistry2.proxies;


import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.item.ModBasicItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {

        ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        modelMesher.register(ModBasicItems.itemTesting, 0, new ModelResourceLocation(Chemistry.MODID + ":testing", "inventory"));
        ModelBakery.addVariantName(ModBasicItems.itemTesting, Chemistry.MODID + ":" + ModBasicItems.itemTesting.name);

        modelMesher.register(ModBasicItems.itemTesting, 0, new ModelResourceLocation(Chemistry.MODID + ":atom", "inventory"));
        ModelBakery.addVariantName(ModBasicItems.itemTesting, Chemistry.MODID + ":" + ModBasicItems.itemTesting.name);

    }
}
