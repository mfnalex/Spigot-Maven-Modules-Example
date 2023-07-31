package com.jeff_media.mymultiversionplugin.nms_1_12_2;

import com.jeff_media.mymultiversionplugin.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;

public class NMSHandlerImpl implements NMSHandler {
    @Override
    public double[] getTPS() {
        return ((CraftServer) Bukkit.getServer()).getHandle().getServer().recentTps;
    }
}
