package com.jeff_media.mymultiversionplugin.examples;

import com.jeff_media.mymultiversionplugin.MyMultiversionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarTask implements Runnable {

    private final MyMultiversionPlugin plugin;

    public ActionBarTask(MyMultiversionPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            plugin.getNMSHandler().sendActionBar(player, "Current TPS: " + ShowTPSCommand.formatTps(plugin.getNMSHandler().getTPS()[0]));
        }
    }
}
