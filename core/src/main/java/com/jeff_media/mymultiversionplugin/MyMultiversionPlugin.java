package com.jeff_media.mymultiversionplugin;

import com.jeff_media.mymultiversionplugin.examples.ActionBarTask;
import com.jeff_media.mymultiversionplugin.examples.ShowTPSCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMultiversionPlugin extends JavaPlugin {

    private String minecraftVersion;
    private NMSHandler nmsHandler;

    @Override
    public void onEnable() {
        getLogger().info("Hello, world! I'll now try to create an NMSHandler for Minecraft " + getMinecraftVersion());
        nmsHandler = createNMSHandler();

        // Example: Command to show TPS
        getCommand("showtps").setExecutor(new ShowTPSCommand(this));

        // Example: Task to send TPS to all players as actionbar (only requires NMS on 1.8)
        getServer().getScheduler().runTaskTimer(this, new ActionBarTask(this), 0, 20);
    }

    /**
     * Returns the actual running Minecraft version, e.g. 1.20 or 1.16.5
     *
     * @return Minecraft version
     */
    private String getMinecraftVersion() {
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

    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }
}
