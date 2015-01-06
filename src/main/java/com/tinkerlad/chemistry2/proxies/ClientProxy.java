package com.tinkerlad.chemistry2.proxies;


import com.tinkerlad.chemistry2.Chemistry;
import com.tinkerlad.chemistry2.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        renderItem.getItemModelMesher().register(ModItems.itemTesting, 0, new ModelResourceLocation(Chemistry.MODID + ":testing","inventory"));
        ModelBakery.addVariantName(ModItems.itemTesting, Chemistry.MODID + ":" + ModItems.itemTesting.name);
    }
}
