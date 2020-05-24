package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.item.ItemKelpSoup
import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
    @GameRegistry.ObjectHolder(ItemKelpSoup.REGISTRY_NAME)
    lateinit var KELP_SOUP: ItemKelpSoup

    fun register(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seaweed.kelpEnabled && ModConfig.Seaweed.kelpSoupEnabled) registry.register(ItemKelpSoup())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seaweed.kelpEnabled && ModConfig.Seaweed.kelpSoupEnabled) KELP_SOUP.registerItemModel()
    }
}