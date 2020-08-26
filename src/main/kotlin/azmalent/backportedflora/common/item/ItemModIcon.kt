package azmalent.backportedflora.common.item

import azmalent.backportedflora.BackportedFlora
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemModIcon : Item() {
    companion object {
        const val NAME = "mod_icon"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    init {
        setRegistryName(NAME)
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemRenderer(this, 0, registryName.toString())
    }
}