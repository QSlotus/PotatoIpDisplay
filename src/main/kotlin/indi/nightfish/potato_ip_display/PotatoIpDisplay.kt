package indi.nightfish.potato_ip_display

import indi.nightfish.potato_ip_display.integration.PlaceholderIntergration
import indi.nightfish.potato_ip_display.listener.MessageListener
import indi.nightfish.potato_ip_display.listener.PlayerJoinListener
import me.clip.placeholderapi.metrics.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class PotatoIpDisplay : JavaPlugin() {
    lateinit var conf: Config
    override fun onLoad() {
        super.onLoad()
        logger.info("Loading")
    }

    override fun onEnable() {
        super.onEnable()
        val pm = Bukkit.getPluginManager()
        conf = loadConfig(config)

        if (!File(dataFolder, "config.yml").exists()) {
            saveDefaultConfig()
        }

        if (conf.papi.enabled) {
            if (pm.getPlugin("PlaceholderAPI") != null) {
                PlaceholderIntergration(this).register()
            } else {
                throw RuntimeException("PlaceholderAPI enabled in config but NOT installed!")
            }
        }

        logger.info("Registering event -> Listener")
        if (conf.message.playerChat.enabled) {
            // NOTE: formerly "parseOnly", but now we determine if PIPD listens for messages depending on it
            pm.registerEvents(MessageListener(), this)
        }
        if (conf.options.mode == "ip2region" && !File(dataFolder, "ip2region.xdb").exists()) {
            // Save ip2region DB if not exist
            saveResource("ip2region.xdb", false)
        }
        if (conf.options.allowbStats) {
            Metrics(this, 21473)
        }

        pm.registerEvents(PlayerJoinListener(), this)
        logger.info("Ready. Using ${conf.options.mode} mode.")
    }

    override fun onDisable() {
        super.onDisable()
        logger.info("Disabled")
    }
}