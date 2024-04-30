package indi.nightfish.potato_ip_display.parser.provider

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.IpParse
import indi.nightfish.potato_ip_display.util.IpAttributeMap
import org.lionsoul.ip2region.xdb.Searcher
import java.io.File
import java.util.concurrent.CompletableFuture


class Ip2regionParser(private val ip: String) : IpParse {
    private val plugin = PotatoIpDisplay.plugin
    private val dataFolder get() = plugin.dataFolder
    private val dbPath = File(dataFolder, "ip2region.xdb").toPath().toString()
    private val unknown: String = "未知"
    private val xdbBuffer = plugin.conf.options.xdbBuffer

    private val searcher by lazy {
        when (xdbBuffer) {
            "none" -> Searcher.newWithFileOnly(dbPath)
            "vindex" -> Searcher.newWithVectorIndex(dbPath, Searcher.loadVectorIndexFromFile(dbPath))
            "cbuff" -> Searcher.newWithBuffer(Searcher.loadContentFromFile(dbPath))
            else -> throw IllegalArgumentException("Invalid xdbBuffer in config >> $xdbBuffer")
        }
    }

    /* Structure of the information returned:
    INDEX:  |    0    |   1    |     2    |    3   |   4   |
            | COUNTRY | REGION | PROVINCE |  CITY  |  ISP  |
    IP1     |   中国   |   0    |   上海    |  上海市 |  联通  |
    IP2     |   美国   |   0    | 加利福尼亚 |    0   |   0   |
    IP3     |    0    |   0    |     0    |  内网IP | 内网IP |

    */

    override fun getCountry(): String {
        val result = getIp2regionDataAsync().split("|")[0]
        return if (result == "0") unknown else result
    }

    override fun getRegion(): String {
        val result = getIp2regionDataAsync().split("|")[1]
        return if (result == "0") unknown else result
    }

    override fun getProvince(): String {
        val result = getIp2regionDataAsync().split("|")[2]
            .replace("省", "")
        return if (result == "0") unknown else result
    }

    override fun getCity(): String {
        val result = getIp2regionDataAsync().split("|")[3]
            .replace("市", "")
        return if (result == "0") unknown else result
    }

    override fun getISP(): String {
        val result = getIp2regionDataAsync().split("|")[4]
        return if (result == "0") unknown else result
    }


    override fun getFallback(): String {
        val values = arrayOf(getProvince(), getCountry(), getCity())
        for (value in values) {
            if (value.isNotBlank() && value != unknown) {
                return value
            }
        }
        return unknown
    }

    private fun getIp2regionDataAsync(): String {
        val map = IpAttributeMap.ip2regionRawDataMap[ip]
        if (map != null) return map

        val future = CompletableFuture<String>()
        val thread = Thread {
            val result = searcher.search(ip)
            future.complete(result)
            IpAttributeMap.ip2regionRawDataMap[ip] = result
        }
        thread.start()
        return future.get()
    }


}