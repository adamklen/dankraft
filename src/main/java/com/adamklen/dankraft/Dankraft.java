package com.adamklen.dankraft;

import com.adamklen.dankraft.block.ModBlocks;
import com.adamklen.dankraft.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Dankraft.MODID, name = Dankraft.NAME, version = Dankraft.VERSION)
public class Dankraft
{
    public static final String MODID = "dankraft";
    public static final String NAME = "Dankraft";
    public static final String VERSION = "4.20";

    public static Logger logger;
    public static TerribleLedger terribleLedger;

    ThcEventHandler eventHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ModItems.init();
        ModBlocks.init();

        eventHandler = new ThcEventHandler();
        try {
            terribleLedger = new TerribleLedger();
        } catch (Exception e) {
            logger.error("Error connecting to ledger: {}", e.getCause());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this.eventHandler);
    }
}
