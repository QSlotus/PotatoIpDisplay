package indi.nightfish.potato_ip_display.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * The /potatoipdisplay command.
 */
class PotatoIpDisplayCommand : CommandExecutor {

    private val labelList = arrayOf("reload", "toggle", "version")
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {

        if (!labelList.contains(label)){ return true }
        if (sender !is Player) return true
        // TODO: command
        return true
    }
}