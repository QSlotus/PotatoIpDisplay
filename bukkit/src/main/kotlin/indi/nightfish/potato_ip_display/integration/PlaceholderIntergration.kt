package indi.nightfish.potato_ip_display.integration

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.IpParseFactory
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player


class PlaceholderIntergration : PlaceholderExpansion() {
    private val plugin = PotatoIpDisplay.plugin

    override fun getAuthor(): String {
        return "[NightFish, yukonisen]"
    }

    override fun getIdentifier(): String {
        return "potatoipdisplay"
    }

    override fun getVersion(): String {
        return plugin.description.version
    }

    override fun onPlaceholderRequest(
        player: Player?,
        params: String
    ): String? {
        if (player == null) { return null}
        val ip: String = IpParseFactory.getPlayerAddress(player)
        val ipParse = IpParseFactory.getIpParse(ip)
        return when (params) {
            "ip" -> ip
            "country" -> ipParse.getCountry()
            "province" -> ipParse.getProvince()
            "city" -> ipParse.getCity()
            "region" -> ipParse.getRegion()
            "isp" -> ipParse.getISP()
            "fallback" -> ipParse.getFallback()
            else -> null
        }
    }

}