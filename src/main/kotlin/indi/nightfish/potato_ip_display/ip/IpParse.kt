package indi.nightfish.potato_ip_display.ip

interface IpParse {
    fun getRegion(): String
    fun getCountry(): String
    fun getProvincial(): String
    fun getCity(): String
    fun getServiceProvider(): String
}