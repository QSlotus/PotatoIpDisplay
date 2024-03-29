package indi.nightfish.potato_ip_display

import com.google.gson.Gson
import indi.nightfish.potato_ip_display.ip.IpGson
import indi.nightfish.potato_ip_display.ip.IpParseFactory
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent


class PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerLoin(event: PlayerLoginEvent) {
        val playerAddress = event.realAddress.hostAddress
        val player = event.player
        val playerName = player.name
        val ipParse = IpParseFactory.getIpParse(playerAddress)
        IpATTRMap.playerIpATTRMap[playerName] = ipParse.getProvincial()
        Bukkit.getServer().logger.info("Player named $playerName connect to proxy from ${ipParse.getServiceProvider()}")

    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.sendMessage("${ChatColor.DARK_GRAY}[${ChatColor.GOLD}PotatoIpDisplay${ChatColor.DARK_GRAY}] ${ChatColor.YELLOW}您当前ip归属地 ${ChatColor.GRAY}[${ChatColor.AQUA}${IpATTRMap.playerIpATTRMap[event.player.name]}${ChatColor.GRAY}]${ChatColor.RESET}")
    }
}

