package azmalent.backportedflora.common.world

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.seaweed.BlockKelp
import azmalent.backportedflora.common.block.seaweed.BlockKelp.Companion.AGE
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.util.WorldGenUtil
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*


class WorldGenKelp : IWorldGenerator {
    override fun generate(rand: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        val biome = WorldGenUtil.getBiomeInChunk(world, chunkX, chunkZ)
        if (ModBlocks.KELP.isBiomeValid(biome) && world.worldType != WorldType.FLAT) {
            if (rand.nextFloat() < ModConfig.Kelp.generationChance) {
                val chunkPos = world.getChunk(chunkX, chunkZ).pos

                for (i in 0 until ModConfig.Kelp.patchAttempts) {
                    val x = rand.nextInt(16) + 8
                    val z = rand.nextInt(16) + 8

                    val yRange = world.getHeight(chunkPos.getBlock(0, 0, 0).add(x, 0, z)).y + 32
                    val y = rand.nextInt(yRange)

                    val pos = chunkPos.getBlock(0, 0, 0).add(x, y, z)
                    generateKelp(world, rand, pos)
                }
            }
        }
    }

    private fun generateKelp(world: World, rand: Random, targetPos: BlockPos) {
        for (i in 0 until ModConfig.Kelp.plantAttempts) {
            val pos = targetPos.add(
                rand.nextInt(8) - rand.nextInt(8),
                rand.nextInt(4) - rand.nextInt(4),
                rand.nextInt(8) - rand.nextInt(8)
            )

            if (!world.isBlockLoaded(pos)) continue

            if (world.getBlockState(pos).block == Blocks.WATER && pos.y < 64) {
                placeKelp(world, pos, rand)
            }
        }
    }

    private fun placeKelp(world: World, pos: BlockPos, rand: Random) {
        val startingAge = rand.nextInt(BlockKelp.MAX_AGE / 2)
        val height = BlockKelp.MAX_AGE - startingAge

        for (i in 0 until height) {
            val kelpPos = pos.up(i)
            val state = ModBlocks.KELP.defaultState.withProperty(AGE, i + startingAge)

            if (ModBlocks.KELP.canBlockStay(world, kelpPos, state)) {
                world.setBlockState(kelpPos, state, 2)
            }
            else break
        }
    }
}