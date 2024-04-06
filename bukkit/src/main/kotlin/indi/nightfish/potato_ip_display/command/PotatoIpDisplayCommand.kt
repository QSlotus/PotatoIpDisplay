package indi.nightfish.potato_ip_display.command

import indi.nightfish.potato_ip_display.PotatoIpDisplay
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * The /potatoipdisplay command.
 */
class PotatoIpDisplayCommand : CommandExecutor {

    private val labelList = arrayOf("reload", "version")
    private val plugin = Bukkit.getPluginManager().getPlugin("PotatoIpDisplay") as PotatoIpDisplay
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {

        return true
    }
}