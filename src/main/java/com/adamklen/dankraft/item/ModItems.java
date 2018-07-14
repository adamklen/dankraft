package com.adamklen.dankraft.item;

import com.adamklen.dankraft.Dankraft;
import com.adamklen.dankraft.block.BlockThc;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= Dankraft.MODID)
public class ModItems {
    public static Item thcItem;

    public static void init() {
        thcItem = new ItemThc();
        thcItem.setMaxStackSize(1000);
        Dankraft.logger.warn("item.ModItems");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(thcItem);
        Dankraft.logger.warn("Registered items");
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(thcItem);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}
