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

class PconlineParser(private val ip: String) : IpParse {
    private val get = getPconlineDataAsync()
    private val unknown: String = "未知"
    override fun getRegion(): String =
        get["region"]?.asString ?: unknown

    override fun getCountry(): String {
        return when {
            !get["pro"]?.asString.isNullOrBlank() -> "中国"
            get["proCode"]?.asString == "999999" -> "海外"
            else -> unknown
        }
    }

    override fun getProvince(): String =
        get["pro"]?.asString?.replace("省", "") ?: unknown

    override fun getCity(): String =
        get["city"]?.asString?.replace("市", "") ?: unknown

    override fun getISP(): String =
        get["addr"]?.asString ?: unknown

    private fun getPconlineDataAsync(): JsonObject {
        val map = IpAttributeMap.pconlineRawDataMap[ip]
        if (map != null) return map

        val future = CompletableFuture<JsonObject>()
        val thread = Thread {
            val httpClient = HttpClient.newHttpClient()
            /* We use httpclient since HttpURLConnection uses the incorrect encoding */
            val url = URI.create("https://whois.pconline.com.cn/ipJson.jsp?ip=$ip&json=true")
            val request = HttpRequest.newBuilder(url)
                .GET()
                .header("Accept", "application/json")
                .build()
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            val jsonObject = Gson().fromJson(response.body(), JsonObject::class.java)
            future.complete(jsonObject)
            IpAttributeMap.pconlineRawDataMap[ip] = jsonObject
        }
        thread.start()
        return future.get()
    }
}