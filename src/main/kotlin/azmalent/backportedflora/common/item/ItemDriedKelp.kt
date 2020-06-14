package azmalent.backportedflora.common.item

import azmalent.backportedflora.BackportedFlora
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemDriedKelp : ItemFood(1, 0.6f, false) {
    companion object {
        const val NAME = "dried_kelp"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    init {
        setRegistryName(NAME)
        translationKey = NAME
        creativeTab = BackportedFlora.creativeTab
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemRenderer(this, 0, registryName.toString())
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int {
        return 16
    }
}