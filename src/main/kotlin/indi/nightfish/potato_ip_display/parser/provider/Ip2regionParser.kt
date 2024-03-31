package indi.nightfish.potato_ip_display.parser.provider

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.IpParse
import org.bukkit.Bukkit
import org.lionsoul.ip2region.xdb.Searcher


class Ip2regionParser(private val ip: String) : IpParse {
    private val dbPath: String = "plugins/PotatoIpDisplay/ip2region.xdb"
    private val unknown: String = "未知"
    private val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
    private val xdbBuffer = plugin.conf.options.xdbBuffer


    private val searcher: Searcher = when (xdbBuffer) {
        "none" -> {
            Searcher.newWithFileOnly(dbPath)
        }
        "vindex" -> {
            val vIndex: ByteArray = Searcher.loadVectorIndexFromFile(dbPath)
            Searcher.newWithVectorIndex(dbPath, vIndex)
        }
        "cbuff" -> {
            val cBuff: ByteArray = Searcher.loadContentFromFile(dbPath)
            Searcher.newWithBuffer(cBuff)
        }
        else -> {
            throw IllegalArgumentException("Invalid xdbBuffer in config >> $xdbBuffer")
        }
    }


    /* Structure of the information returned:
    INDEX:  |    0    |   1    |     2    |    3   |   4   |
            | COUNTRY | REGION | PROVINCE |  CITY  |  ISP  |
    IP1     |   中国   |   0    |   上海    |  上海市 |  联通  |
    IP2     |   美国   |   0    | 加利福尼亚 |    0   |   0   |
    IP3     |    0    |   0    |     0    |  内网IP | 内网IP |

    */

    override fun getRegion(): String {
        // ip2region doesn't seem to be able to return region information
        val region = try {
            searcher.search(ip).split("|")[1]
        } catch (e: Exception) { "" }
        return if (region == "0") unknown else region
    }

    override fun getCountry(): String {
        val country = try {
            searcher.search(ip).split("|")[0]
        } catch (e: Exception) { "" }
        return if (country == "0") unknown else country
    }

    override fun getProvince(): String {
        val province = try {
            searcher.search(ip).split("|")[2].replace("省", "")
        } catch (e: Exception) { "" }
        // If null replace with getCountry()
        return if (province == "0") unknown else province
    }


    override fun getCity(): String {
        val city = try {
            searcher.search(ip).split("|")[3].replace("市", "")
        } catch (e: Exception) { "" }
        return if (city == "0") unknown else city
    }

    override fun getISP(): String {
        val isp = try {
            searcher.search(ip).split("|")[4]
        } catch (e: Exception) { "" }
        return if (isp == "0") unknown else isp
    }
}