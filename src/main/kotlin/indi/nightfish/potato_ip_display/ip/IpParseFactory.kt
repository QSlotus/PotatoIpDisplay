package indi.nightfish.potato_ip_display.ip

object IpParseFactory {
    fun getIpParse(ip: String): IpParse{
        return WebIpParse(ip)
    }
}