package indi.nightfish.potato_ip_display.listener

import indi.nightfish.potato_ip_display.util.IpAttributeMap
import indi.nightfish.potato_ip_display.PotatoIpDisplay
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent


class MessageListener : Listener {
    private val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
    private val conf = plugin.conf
    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val playerName = event.player.name
        var msg = event.message
        val ipAttr = IpAttributeMap.playerIpAttributeMap[playerName] ?: "未知"

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            msg = PlaceholderAPI.setPlaceholders(event.player, msg);
        }
        Bukkit.getServer().broadcastMessage(conf.message.playerChat.string
            .replace("%ipAttr%", ipAttr)
            .replace("%playerName%", playerName)
            .replace("%msg%", msg))
        event.isCancelled = true
    }
}