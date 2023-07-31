package com.jeff_media.mymultiversionplugin.nms_1_19_4;

import com.jeff_media.mymultiversionplugin.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;

public class NMSHandlerImpl implements NMSHandler {
    @Override
    public double[] getTPS() {
        return ((CraftServer) Bukkit.getServer()).getHandle().getServer().recentTps;
    }
}
