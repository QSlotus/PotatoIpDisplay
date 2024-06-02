package indi.nightfish.potato_ip_display.parser

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.PotatoIpDisplay.Instance.plugin
import indi.nightfish.potato_ip_display.parser.provider.Ip2regionParser
import indi.nightfish.potato_ip_display.parser.provider.IpApiParser
import indi.nightfish.potato_ip_display.parser.provider.PconlineParser
import indi.nightfish.potato_ip_display.util.IpAttributeMap
import org.bukkit.entity.Player
import java.util.logging.Level


object IpParseFactory {

    fun getIpParse(ip: String): IpParse{
        val plugin = PotatoIpDisplay.plugin
        return when (val mode = plugin.conf.options.mode) {
            "pconline" -> PconlineParser(ip)
            "ip2region" -> Ip2regionParser(ip)
            "ip-api" -> IpApiParser(ip)
            else -> throw IllegalArgumentException("Invalid mode in config >> $mode")
        }
    }

    fun getPlayerAddress(player: Player, fallback: String = "0.0.0.0"): String {
        val playerName = player.name
        val map = IpAttributeMap.playerPermsMap[playerName]
        if (map != null) return map

        val prefix = "potatoipdisplay.override."
        val original = player.address?.address.toString().replace("/", "")
        val override = player.effectivePermissions.find { it.permission.startsWith(prefix) }

        return override?.let {
            val overrideIP = it.permission.substring(prefix.length).replace("-", ".")
            if (regexValidated(overrideIP)) {
                IpAttributeMap.playerPermsMap[playerName] = overrideIP
                return overrideIP
            } else {
                plugin.log("Invalid IP for $playerName from permission: ${override.permission}! using fallback $fallback", Level.WARNING)
                if (original != "null") return original
                else return fallback
            }
        } ?: run {
            if (original != "null") return original
            else return fallback
        }

    }

    fun regexValidated(ip: String): Boolean {
        val ipRegex =
            """(\b25[0-5]|\b2[0-4][0-9]|\b[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}""".toRegex()
        return ip.matches(ipRegex)
    }

}