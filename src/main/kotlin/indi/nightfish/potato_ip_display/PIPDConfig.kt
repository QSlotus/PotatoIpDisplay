package indi.nightfish.potato_ip_display

import org.bukkit.configuration.file.FileConfiguration

data class PIPDConfig(
    val configVersion: Int,
    val options: Options,
    val message: Message,
    val papi: PAPISupport,
) {
    data class Options(
        val mode: String
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
        val format: String
    )
}

fun loadPIPDConfig(fc: FileConfiguration): PIPDConfig {
    return PIPDConfig(
        fc.getInt("config-version"),

        PIPDConfig.Options(
            fc.getString("options.mode")
                ?: "ip2region"
        ),

        PIPDConfig.Message(
            PIPDConfig.Message.PlayerChat(
                fc.getBoolean("messages.player-chat.enabled"),
                fc.getString("messages.player-chat.string")
                    ?: "§7[§b\$ipAttr§7] §f\$playerName §7>> §f\$msg" // FALLBACK CHAT FORMAT
            ),
            PIPDConfig.Message.PlayerLogin(
                fc.getBoolean("messages.player-login.enabled"),
                fc.getString("messages.player-login.string")
                    ?: "§7[§6PotatoIpDisplay§7] §e您当前ip归属地 §7[§b\$ipAttr§7]"
            )
        ),

        PIPDConfig.PAPISupport(
            fc.getBoolean("papi.enabled"),
            fc.getString("papi.format") ?: ""

        )
    )
}
