package love.chihuyu.chihuyuutils.item

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class ItemStackBuilder(material: Material): ItemStack(material), Listener {

    constructor(material: Material, builder: (ItemStack.() -> Unit) = {}) : this(material) {
        ItemStack(material).apply(builder)
    }
}