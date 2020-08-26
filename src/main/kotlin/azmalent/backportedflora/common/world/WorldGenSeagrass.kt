package azmalent.backportedflora.common.world

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

class WorldGenSeagrass : IWorldGenerator {
    companion object {
        const val GENERATION_ATTEMPTS = 64
    }

    override fun generate(rand: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        if (world.worldType != WorldType.FLAT) {
            val chunkPos = world.getChunk(chunkX, chunkZ).pos

            for (i in 0..8) {
                val x = rand.nextInt(16) + 8
                val z = rand.nextInt(16) + 8

                val yRange = world.getHeight(chunkPos.getBlock(0, 0, 0).add(x, 0, z)).y + 32
                val y = rand.nextInt(yRange)

                val pos = chunkPos.getBlock(0, 0, 0).add(x, y, z)
                generateSeagrass(world, rand, pos)
            }
        }
    }

    private fun generateSeagrass(world: World, rand: Random, targetPos: BlockPos) {
        for (i in 0 until GENERATION_ATTEMPTS) {
            val pos = targetPos.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            )

            if (!world.isBlockLoaded(pos)) continue

            if (world.getBlockState(pos).block == Blocks.WATER && pos.y < 64) {
                placeSeagrass(world, pos, rand)
            }
        }
    }

    private fun placeSeagrass(world: World, pos: BlockPos, rand: Random) {
        val state = ModBlocks.SEAGRASS.defaultState

        if (ModBlocks.SEAGRASS.canBlockStay(world, pos, state)) {
            world.setBlockState(pos, state, 2)

            //50% to generate it 2 block high (if possible)
            if (rand.nextDouble() < 0.5) {
                if (ModBlocks.SEAGRASS.canBlockStay(world, pos.up(), state)) {
                    world.setBlockState(pos.up(), state, 2)
                }
            }
        }
    }
}