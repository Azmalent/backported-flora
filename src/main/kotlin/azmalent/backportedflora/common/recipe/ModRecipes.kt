package azmalent.backportedflora.common.recipe

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.registry.ModItems
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

object ModRecipes {
    fun register() {
        registerSmelting()
    }

    private fun registerSmelting() {
        if (ModConfig.Seaweed.kelpEnabled && ModConfig.Seaweed.driedKelpEnabled) {
            GameRegistry.addSmelting(
                ModBlocks.KELP.itemBlock,
                ItemStack(ModItems.DRIED_KELP),
                0.1f
            )
        }
    }
}