package love.chihuyu.chihuyulib

import org.bukkit.plugin.java.JavaPlugin

class Plugin: JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    init {
        plugin = this
    }
}