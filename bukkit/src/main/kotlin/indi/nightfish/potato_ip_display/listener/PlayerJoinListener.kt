package indi.nightfish.potato_ip_display.listener

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.IpParseFactory
import indi.nightfish.potato_ip_display.util.IpAttributeMap
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.scheduler.BukkitRunnable


class PlayerJoinListener: Listener {
    val plugin = PotatoIpDisplay.plugin
    private val conf = plugin.conf

    @EventHandler
    fun onPlayerLogin(event: PlayerLoginEvent) {

        object : BukkitRunnable() {
                override fun run() {
                    val playerName = event.player.name
                    val originalIP = event.address.hostAddress

                    /* Regenerate player permission override cache on join */
                    IpAttributeMap.playerIpAddressMap.remove(playerName)

                    val playerAddress = IpParseFactory.getPlayerAddress(event.player, originalIP)
                    val ipParse = IpParseFactory.getIpParse(playerAddress)
                    val result = ipParse.getFallback()
                    IpAttributeMap.playerIpAttributeMap[playerName] = result
                    plugin.log("Player named $playerName connected from ${ipParse.getProvince()}${ipParse.getCity()} ${ipParse.getISP()}")
                }
            }.run()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val ipAttr = IpAttributeMap.playerIpAttributeMap[event.player.name] ?: "未知"
        if (conf.message.playerLogin.enabled) {
            event.player.sendMessage(conf.message.playerLogin.string
                .replace("%ipAttr%", ipAttr))
        }
    }
}

