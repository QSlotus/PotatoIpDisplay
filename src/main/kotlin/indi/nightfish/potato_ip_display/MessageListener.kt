package indi.nightfish.potato_ip_display

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent


class MessageListener: Listener{
        @EventHandler(priority = EventPriority.LOWEST)
        fun onPlayerChat(event: AsyncPlayerChatEvent) {
                val parseOnly = PotatoPlugin.fileConfig.getBoolean("parseOnly", true)
                if (!parseOnly) {
                val player = event.player
                val playerName = player.name
                var msg = event.message
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        msg = PlaceholderAPI.setPlaceholders(event.player, msg);
                }
                Bukkit.getServer().broadcastMessage("${ChatColor.GRAY}[${ChatColor.AQUA}${IpATTRMap.playerIpATTRMap[playerName]}${ChatColor.GRAY}] ${ChatColor.RESET}$playerName ${ChatColor.GRAY}>> ${ChatColor.RESET}$msg")
                event.isCancelled = true
                }
        }
}