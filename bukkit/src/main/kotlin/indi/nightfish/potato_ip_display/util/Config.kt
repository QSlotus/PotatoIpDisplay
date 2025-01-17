package indi.nightfish.potato_ip_display.util

import org.bukkit.configuration.file.FileConfiguration

data class Config(
    val configVersion: Int = 1,
    val pluginConfigVersion: Int,
    val options: Options,
    val message: Message,
    val papi: PAPISupport,
) {
    data class Options(
        val mode: String,
        val xdbBuffer: String,
        val allowbStats: Boolean
    )

    data class Message(
        val playerChat: PlayerChat,
        val playerLogin: PlayerLogin
    ) {
        data class PlayerChat(
            val enabled: Boolean,
            val string: String
        )

        data class PlayerLogin(
            val enabled: Boolean,
            val string: String
        )
    }

    data class PAPISupport(
        val enabled: Boolean,
    )
}

fun loadConfig(fc: FileConfiguration): Config {
    return Config(
        fc.getInt("config-version"),
        1, // plugin reserved config version

        Config.Options(
            fc.getString("options.mode") ?: "ip2region",
            fc.getString("options.xdb-buffer") ?: "vindex",
            fc.getBoolean("options.allow-bstats")
        ),


        Config.Message(
            Config.Message.PlayerChat(
                fc.getBoolean("messages.player-chat.enabled"),
                fc.getString("messages.player-chat.string")
                    ?: "§7[§b\$ipAttr§7] §f\$playerName §7>> §f\$msg"  // FALLBACK CHAT FORMAT
            ),
            Config.Message.PlayerLogin(
                fc.getBoolean("messages.player-login.enabled"),
                fc.getString("messages.player-login.string")
                    ?: "§7[§6PotatoIpDisplay§7] §e您当前ip归属地 §7[§b\$ipAttr§7]"
            )
        ),

        Config.PAPISupport(
            fc.getBoolean("papi.enabled")
        )
    )
}
