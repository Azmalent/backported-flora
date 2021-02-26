package azmalent.backportedflora

import azmalent.backportedflora.client.ModCreativeTab
import azmalent.backportedflora.common.handler.ModFuelHandler
import azmalent.backportedflora.common.recipe.ModRecipes
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.registry.ModItems
import azmalent.backportedflora.common.registry.ModSoundEvents
import azmalent.backportedflora.common.registry.ModWorldgen
import azmalent.backportedflora.proxy.IProxy
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import org.apache.logging.log4j.Logger
import java.io.File


@Mod(modid = BackportedFlora.MODID, name = BackportedFlora.NAME, version = BackportedFlora.VERSION, modLanguageAdapter = BackportedFlora.ADAPTER)
@Mod.EventBusSubscriber
object BackportedFlora {
    const val MODID = "backportedflora"
    const val NAME = "Backported Flora"
    const val VERSION = "1.7"
    const val CONFIG_FILE_NAME = "$MODID.cfg"

    const val ADAPTER = "net.shadowfacts.forgelin.KotlinAdapter"
    const val SERVER_PROXY = "azmalent.backportedflora.proxy.ServerProxy"
    const val CLIENT_PROXY = "azmalent.backportedflora.proxy.ClientProxy"

    @Mod.Instance
    lateinit var instance: BackportedFlora

    val creativeTab = ModCreativeTab()

    @SidedProxy(serverSide = SERVER_PROXY, clientSide = CLIENT_PROXY)
    lateinit var proxy: IProxy

    lateinit var config: Configuration
    lateinit var LOGGER: Logger

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        LOGGER = event.modLog

        val configDir = event.modConfigurationDirectory
        config = Configuration(File(configDir, CONFIG_FILE_NAME))
        ModConfig.sync()

        proxy.preInit(event)
    }

    @Mod.EventHandler
    @Suppress("DEPRECATION")
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)
        GameRegistry.registerFuelHandler(ModFuelHandler())
        ModWorldgen.register()
        ModRecipes.register()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
        ModItems.initOreDictionary()
    }

    @SubscribeEvent
    @JvmStatic fun onRegisterBlocks(event: RegistryEvent.Register<Block>) {
        LOGGER.info("Registering blocks")
        ModBlocks.register(event.registry)
    }

    @SubscribeEvent
    @JvmStatic fun onRegisterItems(event: RegistryEvent.Register<Item>) {
        LOGGER.info("Registering items")
        ModBlocks.registerItemBlocks(event.registry)
        ModItems.register(event.registry)
    }

    @SubscribeEvent
    @JvmStatic fun onRegisterSoundEvents(event: RegistryEvent.Register<SoundEvent>) {
        LOGGER.info("Registering sounds")
        ModSoundEvents.register(event.registry)
    }
}
