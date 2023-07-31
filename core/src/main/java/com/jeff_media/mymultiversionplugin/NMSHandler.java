package com.jeff_media.mymultiversionplugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public interface NMSHandler {

    // Even in 1.20 API, you need NMS for this
    double[] getTPS();

    // This method only needs to be overridden in versions that don't support this through API (1.8)
    default void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}
