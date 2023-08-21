package com.jeff_media.mymultiversionplugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GetSkullCommand implements CommandExecutor {
    private final MyMultiversionPlugin plugin;
    public GetSkullCommand(MyMultiversionPlugin myMultiversionPlugin) {
        this.plugin = myMultiversionPlugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String base64ChocoBall = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiMDdjMDhiMDc4YTkyNzRhYWNiNGQ4NDY4N2MyYTRmM2IzMDhmNzJiNGNmOTMzMzhiMWE1ZjVhZTMzMGUxIn19fQ==";
        SkullMeta meta = plugin.getNMSHandler().getSkullWithBase64(base64ChocoBall);
        ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM);
        item.setItemMeta(meta);
        ((Player)commandSender).getInventory().addItem(item);
        return true;
    }
}
