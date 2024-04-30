package indi.nightfish.potato_ip_display

import indi.nightfish.potato_ip_display.command.PotatoIpDisplayCommand
import indi.nightfish.potato_ip_display.integration.PlaceholderIntergration
import indi.nightfish.potato_ip_display.listener.MessageListener
import indi.nightfish.potato_ip_display.listener.PlayerJoinListener
import indi.nightfish.potato_ip_display.util.Config
import indi.nightfish.potato_ip_display.util.UpdateUtil
import indi.nightfish.potato_ip_display.util.loadConfig
import me.clip.placeholderapi.metrics.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Level

class PotatoIpDisplay : JavaPlugin() {

    companion object Instance {
        lateinit var instance: PotatoIpDisplay
        val plugin by lazy { instance }
    }

    lateinit var conf: Config

    override fun onLoad() {
        super.onLoad()
        reloadConfig()
    }

    override fun onEnable() {
        super.onEnable()
        instance = this
        initPlugin()
        initResources()

        if (conf.options.allowbStats) Metrics(this, 21473)
        log("PotatoIpDisplay has been enabled. [mode: ${conf.options.mode}]")
    }

    override fun onDisable() {
        super.onDisable()
        log("Disabled")
    }

    private fun initResources() {
        val configFile = File(dataFolder, "config.yml")
        val dbFile = File(dataFolder, "ip2region.xdb")
        val authors = this.description.authors
        val isLite = false
        val ip2regionDatabaseURL =
            "https://raw.githubusercontent.com/lionsoul2014/ip2region/master/data/ip2region.xdb"

        if ("yukonisen" !in authors || "NightFish" !in authors) this.isEnabled = false
        if (!configFile.exists()) this.saveDefaultConfig()

        if (conf.options.mode == "ip2region" && !dbFile.exists()) {
            if (isLite) {
                UpdateUtil.downloadDatabase(ip2regionDatabaseURL, dbFile.toPath())
                // TODO: Download db files from internet for lite builds
            } else { /* For non-lite builds, ip2region.xdb included in jar */
                saveResource("ip2region.xdb", false)
                log("ip2region.xdb saved to plugin directory.")
            }
        }

    }

    fun initPlugin() {
        val pm = Bukkit.getPluginManager()
        conf = loadConfig(config)

        PlaceholderIntergration().unregister()
        if (conf.papi.enabled) {
            if (pm.getPlugin("PlaceholderAPI") != null) {
                PlaceholderIntergration().register()
            } else throw RuntimeException("PlaceholderAPI enabled in config but NOT installed!")
        }

        /* Unregistering events */
        HandlerList.unregisterAll()

        /* Registering events */
        if (conf.message.playerChat.enabled)
            pm.registerEvents(MessageListener(), this)
        if (conf.message.playerLogin.enabled)
            pm.registerEvents(PlayerJoinListener(), this)

        /* Registering commands */
        getCommand("potatoipdisplay")!!.setExecutor(PotatoIpDisplayCommand())
        getCommand("pipd")!!.setExecutor(PotatoIpDisplayCommand())
    }

    fun log(message: String, level: Level = Level.INFO) =
        logger.log(level, message)
}