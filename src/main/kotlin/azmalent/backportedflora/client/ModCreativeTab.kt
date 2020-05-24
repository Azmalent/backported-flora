package azmalent.backportedflora.client

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

class ModCreativeTab : CreativeTabs(BackportedFlora.MODID) {
    override fun createIcon(): ItemStack {
        return ItemStack(ModBlocks.KELP.itemBlock)
    }

    override fun hasSearchBar(): Boolean {
        return true
    }
}