package indi.nightfish.potato_ip_display

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class PotatoIpDisplay : JavaPlugin() {
    lateinit var conf: PIPDConfig
    override fun onLoad() {
        super.onLoad()
        logger.info("Loading")
    }

    override fun onEnable() {
        super.onEnable()
        val pm = Bukkit.getPluginManager()
        conf = loadPIPDConfig(config)

        if (!File(dataFolder, "config.yml").exists()) {
            saveDefaultConfig()
        }

        /*if (pm.getPlugin("PlaceholderAPI") == null) {
            logger.warning("Could not find PlaceholderAPI! The placeholders will not work!");
        }*/

        logger.info("Registering event -> Listener")
        if (conf.message.playerChat.enabled) {
            // NOTE: formerly "parseOnly", but now we determine if PIPD listens for messages depending on it
            pm.registerEvents(MessageListener(), this)
        }
        if (conf.options.mode == "ip2region" && !File(dataFolder, "ip2region.xdb").exists()) {
            logger.warning("ip2region.xdb NOT FOUND! place into \"plugins/PotatoIpDisplay/ip2region.xdb\"")
        }

        pm.registerEvents(PlayerJoinListener(), this)
        logger.info("Ready. Using ${conf.options.mode} mode.")
    }

    override fun onDisable() {
        super.onDisable()
        logger.info("Disabled")
    }
}