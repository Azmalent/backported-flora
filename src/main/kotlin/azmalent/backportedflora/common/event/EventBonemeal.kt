package azmalent.backportedflora.common.event

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.flower.AbstractFlower
import azmalent.backportedflora.common.block.seaweed.AbstractSeaweed
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraft.item.ItemDye
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.entity.player.BonemealEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object EventBonemeal {
    private const val FLOWER_GENERATION_ATTEMPTS = 4
    private const val SEAGRASS_GENERATION_ATTEMPTS = 8

    private val FLOWER_ALLOWED_SOILS = setOf(
            Blocks.GRASS, Blocks.DIRT, Blocks.FARMLAND
    )

    @SubscribeEvent
    @JvmStatic fun onBonemealUsed(event: BonemealEvent) {
        val world = event.world
        if (world.isRemote) return

        val pos = event.pos
        val block = event.block

        val up = pos.up()
        if (world.isAirBlock(up)) {
            when (block.block) {
                in FLOWER_ALLOWED_SOILS -> {
                    if (ModConfig.Flowers.cornflowerEnabled) {
                        growFlowers(up, world, ModBlocks.CORNFLOWER)
                    }
                    if (ModConfig.Flowers.lilyOfTheValleyEnabled) {
                        growFlowers(up, world, ModBlocks.LILY_OF_THE_VALLEY)
                    }
                    event.result = Event.Result.DEFAULT
                }
                Blocks.SOUL_SAND -> {
                    if (ModConfig.Flowers.witherRoseEnabled) {
                        growFlowers(up, world, ModBlocks.WITHER_ROSE)
                    }
                    Minecraft.getMinecraft().player.swingArm(event.hand)
                    event.result = Event.Result.ALLOW
                }

            }
        } else if (world.getBlockState(up).material == Material.WATER) {
            if (block.material in AbstractSeaweed.ALLOWED_SOILS) {
                if (ModConfig.Seaweed.seagrassEnabled) {
                    growSeagrass(up, world)
                }
                Minecraft.getMinecraft().player.swingArm(event.hand)
                event.result = Event.Result.ALLOW
            }
        }
    }

    private fun growFlowers(pos: BlockPos, world: World, flower: AbstractFlower) {
        val rand = world.rand

        for (i in 0 until FLOWER_GENERATION_ATTEMPTS) {
            val blockPos = pos.add(
                    rand.nextInt(3) - rand.nextInt(3),
                    rand.nextInt(2) - rand.nextInt(2),
                    rand.nextInt(3) - rand.nextInt(3)
            )

            if (world.isAirBlock(blockPos)
                    && flower.canPlaceBlockAt(world, blockPos)
                    && flower.isBiomeValid(world.getBiome(blockPos))) {
                world.setBlockState(blockPos, flower.defaultState)
            }
        }
    }

    private fun growSeagrass(pos: BlockPos, world: World) {
        val rand = world.rand

        for (i in 0 until SEAGRASS_GENERATION_ATTEMPTS) {
            val blockPos = pos.add(
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(2) - rand.nextInt(2),
                    rand.nextInt(4) - rand.nextInt(4)
            )

            if (world.getBlockState(blockPos).block == Blocks.WATER &&
                    ModBlocks.SEAGRASS.canPlaceBlockAt(world, blockPos)) {
                world.setBlockState(blockPos, ModBlocks.SEAGRASS.defaultState)
            }
        }
    }
}