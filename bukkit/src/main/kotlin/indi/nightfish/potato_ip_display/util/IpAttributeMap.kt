package indi.nightfish.potato_ip_display.util

import com.google.gson.JsonObject

object IpAttributeMap {
    val ip2regionRawDataMap: MutableMap<String, String> = mutableMapOf()
    val pconlineRawDataMap: MutableMap<String, JsonObject> = mutableMapOf()
    val ipApiRawDataMap: MutableMap<String, JsonObject> = mutableMapOf()
    val playerIpAttributeMap: MutableMap<String, String> = mutableMapOf()
}