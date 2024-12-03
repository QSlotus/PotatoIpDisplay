package indi.nightfish.potato_ip_display.parser.provider

import com.google.gson.Gson
import com.google.gson.JsonObject
import indi.nightfish.potato_ip_display.parser.IpParse
import indi.nightfish.potato_ip_display.util.IpAttributeMap
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class IpApiParser(private val ip: String) : IpParse {
    private val get = getIpApiDataAsync()
    private val unknown: String = "未知"
    private val isReservedRange: Boolean =
        (get["status"]?.asString == "fail") && (get["message"]?.asString == "reserved range")

    override fun getRegion(): String =
        get["regionName"]?.asString ?: unknown

    override fun getCountry(): String =
        get["country"]?.asString ?: unknown

    override fun getProvince(): String =
        get["regionName"]?.asString ?: unknown

    override fun getCity(): String =
        get["city"]?.asString ?: unknown

    override fun getISP(): String {
        if (isReservedRange) return "保留地址"
        return get["isp"]?.asString ?: unknown
    }

    override fun getFallback(): String {
        if (isReservedRange) return "保留地址"
        val values = arrayOf(getProvince(), getCountry(), getCity())
        for (value in values) {
            if (value.isNotBlank() && value != "") return value
        }
        return unknown
    }

    private fun getIpApiDataAsync(): JsonObject {
        val map = IpAttributeMap.ipApiRawDataMap[ip]
        if (map != null) return map

        val future = CompletableFuture<JsonObject>()
        val thread = Thread {
            val httpClient = HttpClient.newHttpClient()
            val url = URI.create("http://ip-api.com/json/$ip?lang=zh-CN")
            try {
                val request = HttpRequest.newBuilder(url)
                    .GET()
                    .header("Accept", "application/json")
                    .build()
                val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
                val jsonObject = Gson().fromJson(response.body(), JsonObject::class.java)
                future.complete(jsonObject)
                if (response.statusCode() == 200) {
                    IpAttributeMap.ipApiRawDataMap[ip] = jsonObject
                }
            } catch (_: Exception) {
                val jsonObject: JsonObject =
                    Gson().fromJson("{\"err\":\"failed\"}", JsonObject::class.java)
                future.complete(jsonObject)
                throw RuntimeException("Error while querying $ip. Common network problem.")
            }
        }
        thread.start()
        return future.get()
    }
}
