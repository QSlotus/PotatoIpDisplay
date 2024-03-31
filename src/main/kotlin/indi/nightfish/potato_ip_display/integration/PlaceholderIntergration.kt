package indi.nightfish.potato_ip_display.integration

import indi.nightfish.potato_ip_display.parser.IpParseFactory
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class PlaceholderIntergration(plugin: JavaPlugin) : PlaceholderExpansion() {
    override fun getAuthor(): String {
        return "NightFish"
    }

    override fun getIdentifier(): String {
        return "potatoipdisplay"
    }

    override fun getVersion(): String {
        return "1.2.0 "
    }

    override fun onPlaceholderRequest(
        player: Player?,
        params: String
    ): String? {
        val ip: String = player?.address?.address.toString().replace("/", "")
        val ipParse = IpParseFactory.getIpParse(ip)
        return when (params) {
            "ip" -> ip
            "country" -> ipParse.getCountry()
            "province" -> ipParse.getProvince()
            "city" -> ipParse.getCity()
            "region" -> ipParse.getRegion()
            "isp" -> ipParse.getISP()
            else -> null
        }
    }

}