package com.jeff_media.mymultiversionplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMultiversionPlugin extends JavaPlugin {

    private String minecraftVersion;

    /**
     * Returns the actual running Minecraft version, e.g. 1.20 or 1.16.5
     *
     * @return Minecraft version
     */
    private final String getMinecraftVersion() {
        if (minecraftVersion != null) {
            return minecraftVersion;
        } else {
            String bukkitGetVersionOutput = Bukkit.getVersion();
            Matcher matcher = Pattern.compile("\\(MC: (?<version>[\\d]+\\.[\\d]+(\\.[\\d]+)?)\\)").matcher(bukkitGetVersionOutput);
            if (matcher.find()) {
                return minecraftVersion = matcher.group("version");
            } else {
                throw new RuntimeException("Could not determine Minecraft version from Bukkit.getVersion(): " + bukkitGetVersionOutput);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private NMSHandler createNMSHandler() {
        String clazzName = "com.jeff_media.mymultiversionplugin.nms_" + getMinecraftVersion()
                .replace(".", "_") + ".NMSHandlerImpl";
        try {
            Class<? extends NMSHandler> clazz = (Class<? extends NMSHandler>) Class.forName(clazzName);
            return clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("This Minecraft version (" + getMinecraftVersion() +
                    ") is not supported by this version of the plugin)", exception);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    private NMSHandler nmsHandler;

    @Override
    public void onEnable() {
        getLogger().info("Hello, world! I'll now try to create an NMSHandler for Minecraft " + getMinecraftVersion());
        nmsHandler = createNMSHandler();

        getCommand("showtps").setExecutor((sender, command, label, args) -> {
            double[] tps = nmsHandler.getTPS();
            sender.sendMessage("TPS 1m: " + formatTps(tps[0]));
            sender.sendMessage("TPS 5m: " + formatTps(tps[1]));
            sender.sendMessage("TPS 15m: " + formatTps(tps[2]));
            return true;
        });
    }

    private String formatTps(double tps) {
        String shortenedTPS = String.format("%.2f", tps);
        if(tps >= 19.9) {
            return ChatColor.GREEN + "" + ChatColor.BOLD + shortenedTPS;
        } else if(tps >= 19) {
            return ChatColor.GREEN + shortenedTPS;
        } else if(tps >= 15) {
            return ChatColor.YELLOW + shortenedTPS;
        } else if(tps >= 10) {
            return ChatColor.RED + shortenedTPS;
        } else {
            return ChatColor.RED + "" + ChatColor.BOLD + shortenedTPS;
        }
    }

}
