package indi.nightfish.potato_ip_display.parser.provider

import com.google.gson.Gson
import indi.nightfish.potato_ip_display.parser.IpGson
import indi.nightfish.potato_ip_display.parser.IpParse

class PconlineParser(ip: String) : IpParse {
    private val parseData: IpGson = Gson().fromJson("https://whois.pconline.com.cn/ipJson.jsp?ip=$ip&json=true", IpGson::class.java)
    override fun getDistrict(): String = parseData.region

    override fun getCountry(): String = if (parseData.pro.replace("省", "") == "") {
        Regex(pattern = """[\u4e00-\u9fa5]+""")
            .find(parseData.addr)?.value ?: ""
    } else {
        "中国"
    }

    override fun getProvince(): String = parseData.pro
    override fun getCity(): String = parseData.city

    override fun getISP(): String = parseData.addr
}