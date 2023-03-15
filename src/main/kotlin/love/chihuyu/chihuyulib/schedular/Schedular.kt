package love.chihuyu.chihuyulib.schedular

import love.chihuyu.chihuyulib.ChihuyuLibPlugin
import org.bukkit.scheduler.BukkitTask

object Schedular {
    fun ChihuyuLibPlugin.sync(function: () -> Unit): BukkitTask = server.scheduler.runTask(this, function)
    fun ChihuyuLibPlugin.sync(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLater(this, function, delay)
    fun ChihuyuLibPlugin.sync(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimer(this, function, delay, period)

    fun ChihuyuLibPlugin.async(function: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, function)
    fun ChihuyuLibPlugin.async(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLaterAsynchronously(this, function, delay)
    fun ChihuyuLibPlugin.async(period: Long, delay: Long = 0, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, function, delay, period)
}