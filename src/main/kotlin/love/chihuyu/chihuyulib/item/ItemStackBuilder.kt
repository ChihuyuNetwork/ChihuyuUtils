package love.chihuyu.chihuyulib.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemStackBuilder {
    fun build(material: Material, builder: (ItemStack.() -> Unit)? = null): ItemStack = if (builder != null) ItemStack(material).apply(builder) else ItemStack(material)
}