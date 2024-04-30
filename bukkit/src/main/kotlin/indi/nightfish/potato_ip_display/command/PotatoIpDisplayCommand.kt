package indi.nightfish.potato_ip_display.command

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import indi.nightfish.potato_ip_display.parser.IpParseFactory
import indi.nightfish.potato_ip_display.util.IpAttributeMap
import indi.nightfish.potato_ip_display.util.UpdateUtil
import indi.nightfish.potato_ip_display.util.loadConfig
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * The /potatoipdisplay command.
 */
class PotatoIpDisplayCommand : TabExecutor {
    private val plugin = PotatoIpDisplay.plugin

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<String>
    ): Boolean {

        if (!sender.hasPermission("potatoipdisplay.command")) {
            sendNoPerms(sender)
            return true
        }

        if (args.isEmpty()) {

            var mode = plugin.conf.options.mode
            if (mode == "ip2region") {
                mode += " [${plugin.conf.options.xdbBuffer}]"
            }


            sendMsg(sender, "§f工作模式: §b$mode§f", true)
            sendMsg(sender, "§7尝试检查更新……", false)
            UpdateUtil.checkForUpdatesAsync { result ->
                sendMsg(sender, result, false)
            }
            return true
        }

        when (args[0].lowercase()) {
            "about" -> {
                sendMsg(sender, "§f版本 §b${plugin.description.version} §fby §b${plugin.description.authors.joinToString(", ")}", true)
                sendMsg(sender, "§6PotatoIPDisplay §f是§a免费§f的开源插件，详见:", false)
                sendMsg(sender, "GitHub§f > §7\n https://github.com/dmzz-yyhyy/PotatoIpDisplay", false)
                sendMsg(sender, "使用文档§f > §7\n https://upt.curiousers.org/docs/PotatoIpDisplay/intro", false)
                return true
            }

            "reload" -> {
                if (!sender.hasPermission("potatoipdisplay.reload")) {
                    sendNoPerms(sender)
                    return true
                }
                plugin.log("PotatoIPDisplay (${plugin.description.version}) 正在尝试重载。若遇到插件问题，请重启服务器。")
                runCatching {
                    plugin.reloadConfig()
                    plugin.conf = loadConfig(plugin.config)
                    plugin.initPlugin()
                }.onFailure {
                    sendMsg(sender, "§c重载失败，请检查控制台输出信息", true)
                }.onSuccess {
                    sendMsg(sender, "§a重载成功！", true)
                }
                return true
            }

            "lookup" -> {
                if (!sender.hasPermission("potatoipdisplay.lookup")) {
                    sendNoPerms(sender)
                    return true
                }
                if (args.size < 2) {
                    sendMsg(sender, "§e/$label lookup [玩家] §f>> 查询在线玩家", false)
                    sendMsg(sender, "§e/$label lookup [IPv4] §f>> 查询 IPv4", false)
                    return true
                }

                val target = args[1]

                val player = Bukkit.getServer().getPlayer(target)
                if (player != null) {
                    sendMsg(sender, "§f查询玩家: §b$target", true)
                    val playerAddress: String = player.address?.address.toString().replace("/", "")
                    sendMsg(sender, lookup(playerAddress), false)
                    return true
                }

                if (validate(target)) {
                    sendMsg(sender, "§f查询 IPv4: §b$target", true)
                    sendMsg(sender, lookup(target), false)
                } else sendMsg(sender, "§c查询的玩家离线，或 IPv4 无效", true)
                return true
            }

            "clear" -> {
                if (args.size < 2) {
                    val map = IpAttributeMap
                    val totalCacheSize =
                        map.ip2regionRawDataMap.size + map.pconlineRawDataMap.size + map.ipApiRawDataMap.size
                    val playerCacheSize = map.playerIpAttributeMap.size
                    sendMsg(sender, "§e/$label clear player §f>> 清除玩家缓存 (当前 §b$playerCacheSize §f项)", false)
                    sendMsg(sender, "§e/$label clear cache §f>> 清除查询缓存 (当前 §b$totalCacheSize §f项)", false)
                    return true
                }

                val clearedItems = clear(args[1].lowercase())
                sendMsg(sender, "§f清除完成，共清除了 §b$clearedItems §f项", true)
                return true
            }

            else -> {
                sendMsg(sender, "§c未知命令", true)
                return true
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        return when {
            !sender.hasPermission("potatoipdisplay.command") -> emptyList()
            args.size == 1 -> listOf("lookup", "reload", "clear", "about").filter {
                it.startsWith(args[0])
            }

            args.size == 2 && (args[0] == "lookup") -> {
                val matchingPlayers =
                    plugin.server.onlinePlayers.map { it.name }.filter { it.startsWith(args[1]) }
                matchingPlayers + listOf("127.0.0.1")
            }

            args.size == 2 && (args[0] == "clear") -> {
                listOf("player", "cache")
            }

            else -> emptyList()
        }
    }

    private fun lookup(ip: String): String {
        val ipParse = IpParseFactory.getIpParse(ip)
        return "§f - IP:  §e$ip \n" +
                "§f - 国家:  §e${ipParse.getCountry()} \n" +
                "§f - 省市:  §e${ipParse.getProvince()} ${ipParse.getCity()} \n" +
                "§f - ISP:  §e${ipParse.getISP()} \n" +
                "§f - IP属地:  §a${ipParse.getFallback()}"
    }

    private fun validate(ip: String): Boolean {
        val ipRegex =
            """(\b25[0-5]|\b2[0-4][0-9]|\b[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}""".toRegex()
        return ip.matches(ipRegex)
    }

    private fun clear(target: String): Int {
        val map = IpAttributeMap
        var clearedItems = 0

        when (target) {
            "player" -> {
                clearedItems += map.playerIpAttributeMap.size
                map.playerIpAttributeMap.clear()
            }

            "cache" -> {
                clearedItems += map.ip2regionRawDataMap.size
                clearedItems += map.pconlineRawDataMap.size
                clearedItems += map.ipApiRawDataMap.size
                map.ip2regionRawDataMap.clear()
                map.pconlineRawDataMap.clear()
                map.ipApiRawDataMap.clear()
            }

            else -> {}
        }
        return clearedItems
    }


    private fun sendMsg(sender: CommandSender, msg: String, showPrefix: Boolean) {
        val prefix = if (showPrefix) "§7[§6PotatoIPDisplay§7] " else ""
        sender.sendMessage("$prefix$msg")
    }

    private fun sendNoPerms(sender: CommandSender) {
        sender.sendMessage("§c您没有使用此命令的权限")
    }

}