package indi.nightfish.potato_ip_display

import indi.nightfish.potato_ip_display.ip.IpParseFactory
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent


class PlayerJoinListener : Listener {
    private val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
    private val conf = plugin.conf

    @EventHandler
    fun onPlayerLogin(event: PlayerLoginEvent) {
        val playerAddress = event.realAddress.hostAddress
        val playerName = event.player.name
        val ipParse = IpParseFactory.getIpParse(playerAddress)
        IpATTRMap.playerIpATTRMap[playerName] = ipParse.getProvince()
        Bukkit.getServer().logger.info("Player named $playerName connect to proxy from ${ipParse.getISP()}")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val ipAttr = IpATTRMap.playerIpATTRMap[event.player.name] ?: "未知"
        if (conf.message.playerLogin.enabled) {
            event.player.sendMessage(conf.message.playerLogin.string
                .replace("%ipAttr%", ipAttr))
        }
    }
}

