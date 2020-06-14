package azmalent.backportedflora.common.handler

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.IFuelHandler

class ModFuelHandler : IFuelHandler{
    override fun getBurnTime(fuel: ItemStack?): Int {
        val item = fuel?.item ?: return 0

        if (ModConfig.Seaweed.kelpEnabled && ModConfig.Seaweed.driedKelpEnabled) {
            if (item == ModBlocks.DRIED_KELP_BLOCK.itemBlock) return 4000
        }

        return 0
    }
}