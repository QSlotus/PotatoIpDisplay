package indi.nightfish.potato_ip_display.util

import org.bukkit.entity.Player
import com.google.gson.JsonObject

object IpAttributeMap {
    val ip2regionRawDataMap: MutableMap<String, String> = mutableMapOf()
    val pconlineRawDataMap: MutableMap<String, JsonObject> = mutableMapOf()
    val ipApiRawDataMap: MutableMap<String, JsonObject> = mutableMapOf()
    val playerIpAttributeMap: MutableMap<String, String> = mutableMapOf()
    val playerPermsMap: MutableMap<String, String> = mutableMapOf()
}