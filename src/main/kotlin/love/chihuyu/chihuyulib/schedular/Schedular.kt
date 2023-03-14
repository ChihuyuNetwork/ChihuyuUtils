package love.chihuyu.chihuyulib.schedular

import love.chihuyu.chihuyulib.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object Schedular {
    inline fun bukkitRunnable(crossinline block: BukkitRunnable.() -> Unit) = object : BukkitRunnable() {
        override fun run() = block()
    }

    inline fun Plugin.sync(crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTask(this)
    }

    inline fun Plugin.sync(delay: Long, crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTaskLater(this, delay)
    }

    inline fun Plugin.sync(delay: Long = 0, period: Long, crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTaskTimer(this, delay, period)
    }

    inline fun Plugin.async(crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTaskAsynchronously(this)
    }

    inline fun Plugin.async(delay: Long, crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTaskLaterAsynchronously(this, delay)
    }

    inline fun Plugin.async(delay: Long = 0, period: Long, crossinline block: BukkitRunnable.() -> Unit): BukkitTask {
        return bukkitRunnable(block).runTaskTimerAsynchronously(this, delay, period)
    }
}