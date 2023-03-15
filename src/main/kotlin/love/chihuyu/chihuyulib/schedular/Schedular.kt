package love.chihuyu.chihuyulib.schedular

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

object Schedular {
    fun JavaPlugin.sync(function: () -> Unit): BukkitTask = server.scheduler.runTask(this, function)
    fun JavaPlugin.sync(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLater(this, function, delay)
    fun JavaPlugin.sync(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimer(this, function, delay, period)

    fun JavaPlugin.async(function: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, function)
    fun JavaPlugin.async(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLaterAsynchronously(this, function, delay)
    fun JavaPlugin.async(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, function, delay, period)
}