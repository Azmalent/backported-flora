package azmalent.backportedflora.proxy

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.registry.ModItems
import azmalent.backportedflora.common.registry.ModSoundEvents
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.SoundEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(Side.CLIENT)
class ClientProxy : IProxy {
    companion object {
        @SubscribeEvent
        @JvmStatic fun registerModels(event: ModelRegistryEvent) {
            BackportedFlora.logger.info("Registering models")
            ModBlocks.registerModels()
            ModItems.registerModels()
        }
    }

    override fun preInit(event: FMLPreInitializationEvent) {

    }

    override fun init(event: FMLInitializationEvent) {

    }

    override fun postInit(event: FMLPostInitializationEvent) {

    }

    override fun registerItemRenderer(item: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(item, meta, ModelResourceLocation(id))
    }

    override fun registerItemBlockRenderer(itemBlock: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(
                itemBlock, meta, ModelResourceLocation(id, "inventory")
        )
    }
}
