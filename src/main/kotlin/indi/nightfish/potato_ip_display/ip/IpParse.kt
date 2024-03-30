package indi.nightfish.potato_ip_display.ip

interface IpParse {
    fun getRegion(): String
    fun getCountry(): String
    fun getProvince(): String
    fun getCity(): String
    fun getISP(): String
}