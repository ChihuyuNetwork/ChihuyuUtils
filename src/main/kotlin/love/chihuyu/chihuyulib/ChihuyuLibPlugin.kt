package love.chihuyu.chihuyulib

import org.bukkit.plugin.java.JavaPlugin

class ChihuyuLibPlugin: JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    init {
        plugin = this
    }
}