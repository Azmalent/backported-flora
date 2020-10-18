package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.BlockDriedKelp
import azmalent.backportedflora.common.block.seaweed.BlockKelp
import azmalent.backportedflora.common.block.flower.*
import azmalent.backportedflora.common.block.seaweed.BlockSeagrass
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ModBlocks {
    @ObjectHolder(BlockSeagrass.REGISTRY_NAME)
    lateinit var SEAGRASS: BlockSeagrass

    @ObjectHolder(BlockKelp.REGISTRY_NAME)
    lateinit var KELP: BlockKelp

    @ObjectHolder(BlockLilyOfTheValley.REGISTRY_NAME)
    lateinit var LILY_OF_THE_VALLEY: BlockLilyOfTheValley

    @ObjectHolder(BlockCornflower.REGISTRY_NAME)
    lateinit var CORNFLOWER: BlockCornflower

    @ObjectHolder(BlockWitherRose.REGISTRY_NAME)
    lateinit var WITHER_ROSE: BlockWitherRose

    @ObjectHolder(BlockDriedKelp.REGISTRY_NAME)
    lateinit var DRIED_KELP_BLOCK: BlockDriedKelp

    fun register(registry: IForgeRegistry<Block>) {
        if (ModConfig.Seagrass.enabled) registry.register(BlockSeagrass())
        if (ModConfig.Kelp.enabled) {
            registry.register(BlockKelp())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(BlockDriedKelp())
            }
        }

        if (ModConfig.Cornflower.enabled) registry.register(BlockCornflower())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(BlockLilyOfTheValley())
        if (ModConfig.WitherRose.enabled) registry.register(BlockWitherRose())
    }

    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seagrass.enabled) registry.register(SEAGRASS.createItemBlock())
        if (ModConfig.Kelp.enabled) {
            registry.register(KELP.createItemBlock())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(DRIED_KELP_BLOCK.createItemBlock())
            }
        }

        if (ModConfig.Cornflower.enabled) registry.register(CORNFLOWER.createItemBlock())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(LILY_OF_THE_VALLEY.createItemBlock())
        if (ModConfig.WitherRose.enabled) registry.register(WITHER_ROSE.createItemBlock())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seagrass.enabled) SEAGRASS.registerItemModel()
        if (ModConfig.Kelp.enabled) {
            KELP.registerItemModel()
            if (ModConfig.Kelp.driedKelpEnabled) {
                DRIED_KELP_BLOCK.registerItemModel()
            }
        }

        if (ModConfig.Cornflower.enabled) CORNFLOWER.registerItemModel()
        if (ModConfig.LilyOfTheValley.enabled) LILY_OF_THE_VALLEY.registerItemModel()
        if (ModConfig.WitherRose.enabled) WITHER_ROSE.registerItemModel()
    }
}