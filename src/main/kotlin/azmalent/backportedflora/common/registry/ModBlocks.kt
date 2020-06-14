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
        if (ModConfig.Seaweed.seagrassEnabled) registry.register(BlockSeagrass())
        if (ModConfig.Seaweed.kelpEnabled) {
            registry.register(BlockKelp())
            if (ModConfig.Seaweed.driedKelpEnabled) {
                registry.register(BlockDriedKelp())
            }
        }

        if (ModConfig.Flowers.cornflowerEnabled) registry.register(BlockCornflower())
        if (ModConfig.Flowers.lilyOfTheValleyEnabled) registry.register(BlockLilyOfTheValley())
        if (ModConfig.Flowers.witherRoseEnabled) registry.register(BlockWitherRose())
    }

    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seaweed.seagrassEnabled) registry.register(SEAGRASS.createItemBlock())
        if (ModConfig.Seaweed.kelpEnabled) {
            registry.register(KELP.createItemBlock())
            if (ModConfig.Seaweed.driedKelpEnabled) {
                registry.register(DRIED_KELP_BLOCK.createItemBlock())
            }
        }

        if (ModConfig.Flowers.cornflowerEnabled) registry.register(CORNFLOWER.createItemBlock())
        if (ModConfig.Flowers.lilyOfTheValleyEnabled) registry.register(LILY_OF_THE_VALLEY.createItemBlock())
        if (ModConfig.Flowers.witherRoseEnabled) registry.register(WITHER_ROSE.createItemBlock())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seaweed.seagrassEnabled) SEAGRASS.registerItemModel()
        if (ModConfig.Seaweed.kelpEnabled) {
            KELP.registerItemModel()
            if (ModConfig.Seaweed.driedKelpEnabled) {
                DRIED_KELP_BLOCK.registerItemModel()
            }
        }

        if (ModConfig.Flowers.cornflowerEnabled) CORNFLOWER.registerItemModel()
        if (ModConfig.Flowers.lilyOfTheValleyEnabled) LILY_OF_THE_VALLEY.registerItemModel()
        if (ModConfig.Flowers.witherRoseEnabled) WITHER_ROSE.registerItemModel()
    }
}