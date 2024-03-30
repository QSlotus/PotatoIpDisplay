package indi.nightfish.potato_ip_display.ip

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import org.bukkit.Bukkit

object IpParseFactory {

    fun getIpParse(ip: String): IpParse{
        val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
        val conf = plugin.conf
        return when (val mode = conf.options.mode) {
            "pconline" -> PconlineParse(ip)
            "ip2region" -> Ip2regionParse(ip)
            else -> throw IllegalArgumentException("Invalid mode in config >> $mode")
        }
    }
}