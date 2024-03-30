package indi.nightfish.potato_ip_display.ip

import org.lionsoul.ip2region.xdb.Searcher


class Ip2regionParse(private val ip: String) : IpParse {

    private val dbFile: String = "plugins/PotatoIpDisplay/ip2region.xdb"

    private val searcher: Searcher by lazy {
        Searcher.newWithFileOnly(dbFile)
    }

    /* Structure of the information returned:
    INDEX:  |    0    |   1   |     2    |    3   |   4   |
            | COUNTRY |   ?   | PROVINCE |  CITY  |  ISP  |
    IP1     |   中国   |   0   |   上海    |  上海市 |  联通  |
    IP2     |   美国   |   0   | 加利福尼亚 |    0   |   0   |
    IP3     |    0    |   0   |     0    |  内网IP | 内网IP |

    */

    override fun getRegion(): String {
        return "null"
    }

    override fun getCountry(): String {
        val country = try {
            searcher.search(ip).split("|")[0]
        } catch (e: Exception) {
            ""
        }
        // If null replace with getCity()
        return if (country == "0") getCity() else country
    }

    override fun getProvince(): String {
        val province = try {
            searcher.search(ip).split("|")[2].replace("省", "")
        } catch (e: Exception) {
            ""
        }
        // If null replace with getCountry()
        return if (province == "0") getCountry() else province
    }


    override fun getCity(): String {
        return try {
            searcher.search(ip).split("|")[3].replace("市", "")
        } catch (e: Exception) {
            ""
        }
    }

    override fun getISP(): String {
        return try {
            searcher.search(ip).split("|")[4]
        } catch (e: Exception) {
            ""
        }
    }
}