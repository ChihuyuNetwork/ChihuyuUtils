package love.chihuyu.ChihuyuUtils.message

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

object PrefixedMessage {

    fun Plugin.prefixedMessage(player: Player, message: String) {
        player.sendMessage("${ChatColor.GREEN}[$name] $message")
    }

    fun Plugin.prefixedAllMessage(message: String) {
        server.onlinePlayers.forEach {
            it.sendMessage("${ChatColor.GREEN}[$name] $message")
        }
    }

    fun Plugin.warningMessage(player: Player, message: String) {
        player.sendMessage("${ChatColor.YELLOW}[$name] $message")
    }

    fun Plugin.warningAllMessage(message: String) {
        server.onlinePlayers.forEach {
            it.sendMessage("${ChatColor.YELLOW}[$name] $message")
        }
    }

    fun Plugin.errorMessage(player: Player, message: String) {
        player.sendMessage("${ChatColor.RED}[$name] $message")
    }

    fun Plugin.errorAllMessage(message: String) {
        server.onlinePlayers.forEach {
            it.sendMessage("${ChatColor.RED}[$name] $message")
        }
    }

    fun Plugin.infoMessage(player: Player, message: String) {
        player.sendMessage("${ChatColor.GOLD}[$name]${ChatColor.RESET} $message")
    }

    fun Plugin.infoAllMessage(message: String) {
        server.onlinePlayers.forEach {
            it.sendMessage("${ChatColor.GOLD}[$name]${ChatColor.RESET} $message")
        }
    }
}