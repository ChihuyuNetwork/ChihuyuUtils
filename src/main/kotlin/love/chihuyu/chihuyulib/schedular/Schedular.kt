package love.chihuyu.chihuyulib.schedular

import love.chihuyu.chihuyulib.Plugin
import org.bukkit.scheduler.BukkitTask

object Schedular {
    fun Plugin.sync(function: () -> Unit): BukkitTask = server.scheduler.runTask(this, function)
    fun Plugin.sync(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLater(this, function, delay)
    fun Plugin.sync(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimer(this, function, delay, period)

    fun Plugin.async(function: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, function)
    fun Plugin.async(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLaterAsynchronously(this, function, delay)
    fun Plugin.async(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, function, delay, period)
}