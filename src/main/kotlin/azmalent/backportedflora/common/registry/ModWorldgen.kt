package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.flower.AbstractFlower
import azmalent.backportedflora.common.world.WorldGenOverworldFlowers
import azmalent.backportedflora.common.world.WorldGenKelp
import azmalent.backportedflora.common.world.WorldGenNetherFlowers
import azmalent.backportedflora.common.world.WorldGenSeagrass
import net.minecraftforge.fml.common.registry.GameRegistry

object ModWorldgen {
    private fun registerOverworldFlowerGen(flower: AbstractFlower) {
        GameRegistry.registerWorldGenerator(WorldGenOverworldFlowers(flower), 0)
    }

    private fun registerNetherFlowerGen(flower: AbstractFlower) {
        GameRegistry.registerWorldGenerator(WorldGenNetherFlowers(flower), 0)
    }

    fun register() {
        if (ModConfig.Seagrass.enabled) GameRegistry.registerWorldGenerator(WorldGenSeagrass(), 0)
        if (ModConfig.Kelp.enabled) GameRegistry.registerWorldGenerator(WorldGenKelp(), 0)

        if (ModConfig.Cornflower.enabled) registerOverworldFlowerGen(ModBlocks.CORNFLOWER)
        if (ModConfig.LilyOfTheValley.enabled) registerOverworldFlowerGen(ModBlocks.LILY_OF_THE_VALLEY)
        if (ModConfig.WitherRose.enabled) registerNetherFlowerGen(ModBlocks.WITHER_ROSE)
    }
}