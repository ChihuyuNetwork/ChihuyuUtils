package love.chihuyu.chihuyuutils

import org.bukkit.plugin.java.JavaPlugin

class ChihuyuUtilsPlugin: JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    init {
        plugin = this
    }
}