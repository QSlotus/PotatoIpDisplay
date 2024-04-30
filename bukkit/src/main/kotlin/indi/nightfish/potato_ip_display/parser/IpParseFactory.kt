package indi.nightfish.potato_ip_display.parser

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.provider.Ip2regionParser
import indi.nightfish.potato_ip_display.parser.provider.IpApiParser
import indi.nightfish.potato_ip_display.parser.provider.PconlineParser

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
}