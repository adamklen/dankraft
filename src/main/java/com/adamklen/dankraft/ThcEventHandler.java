package com.adamklen.dankraft;

import com.adamklen.dankraft.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ThcEventHandler {
    private Timer updateTimer;
    private Map<EntityPlayer, Long> lastThcCount;

    public ThcEventHandler() {
        lastThcCount = new HashMap<>();
    }

    @SubscribeEvent
    public void initThcCount(PlayerEvent.PlayerLoggedInEvent event) {
        long thcCount = getThcCount(event.player);
        Dankraft.logger.warn("Init thc count: {}", thcCount);
        lastThcCount.put(event.player, thcCount);
    }

    @SubscribeEvent
    public void dropThc(ItemTossEvent event) {
        if (!isThc(event.getEntityItem().getItem())) {
            return;
        }
        updateThcAsync(event.getPlayer());
    }

    @SubscribeEvent
    public void pickupThc(PlayerEvent.ItemPickupEvent event) {
        if (!isThc(event.getStack())) {
            return;
        }
        final EntityPlayer player = event.player;
        updateThcAsync(player);
    }

    @SubscribeEvent
    public void playerDeath(PlayerDropsEvent event) {
        boolean didDropThc = false;
        for (EntityItem entity : event.getDrops()) {
            if (isThc(entity.getItem())) {
                didDropThc = true;
                break;
            }
        }
        if (didDropThc) {
            updateThcAsync(event.getEntityPlayer());
        }
    }

    private boolean isThc(ItemStack stack) {
        return stack.getItem().equals(ModItems.thcItem);
    }

    private void updateThcAsync(final EntityPlayer player) {
        if (updateTimer != null) {
            updateTimer.cancel();
            updateTimer.purge();
            updateTimer = null;
        }
        updateTimer = new Timer();
        updateTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        long thcCount = getThcCount(player);
                        long lastCount = lastThcCount.getOrDefault(player, 0L);
                        long thcDiff = thcCount - lastCount;
                        String from, to;
                        if (thcDiff == 0) {
                            return;
                        } else if (thcDiff > 0) {
                            from = "Notch";
                            to = player.getName();
                        } else {
                            to = "Notch";
                            from = player.getName();
                        }
                        Dankraft.terribleLedger.recordTransaction(from, to, thcDiff);
                        Dankraft.logger.warn("Updated THC diff: {}", thcDiff);
                        lastThcCount.put(player, thcCount);
                    }
                },
                2000
        );
    }

    private long getThcCount(final EntityPlayer player) {
        long thcCount = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (isThc(stack)) {
                thcCount += stack.getCount();
            }
        }
        return thcCount;
    }
}
