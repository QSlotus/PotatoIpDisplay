package indi.nightfish.potato_ip_display.parser

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.provider.Ip2regionParser
import indi.nightfish.potato_ip_display.parser.provider.PconlineParser
import org.bukkit.Bukkit

object IpParseFactory {

    fun getIpParse(ip: String): IpParse{
        val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
        val conf = plugin.conf
        return when (val mode = conf.options.mode) {
            "pconline" -> PconlineParser(ip)
            "ip2region" -> Ip2regionParser(ip)
            else -> throw IllegalArgumentException("Invalid mode in config >> $mode")
        }
    }
}