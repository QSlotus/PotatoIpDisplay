package indi.nightfish.potato_ip_display.ip

import com.google.gson.Gson

class WebIpParse(ip: String) : IpParse {
    private val parseData: IpGson = Gson().fromJson("https://whois.pconline.com.cn/ipJson.jsp?ip=$ip&json=true", IpGson::class.java)
    override fun getRegion(): String = parseData.region

    override fun getCountry(): String = if (parseData.pro.replace("省", "") == "") {
        Regex(pattern = """[\u4e00-\u9fa5]+""")
            .find(parseData.addr)?.value ?: ""
    } else {
        "中国"
    }

    override fun getProvincial(): String = parseData.pro
    override fun getCity(): String = parseData.city

    override fun getServiceProvider(): String = parseData.addr
}