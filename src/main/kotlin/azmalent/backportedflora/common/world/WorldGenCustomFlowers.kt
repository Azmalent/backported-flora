package azmalent.backportedflora.common.world

import azmalent.backportedflora.common.block.flower.AbstractFlower
import azmalent.backportedflora.common.block.seaweed.BlockKelp
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.util.WorldGenUtil
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenBush
import net.minecraft.world.gen.feature.WorldGenFlowers
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

abstract class WorldGenCustomFlowers(private val flower: AbstractFlower) : IWorldGenerator {
    companion object {
        const val GENERATION_CHANCE = 1 / 8f
        const val GENERATION_ATTEMPTS = 64
    }

    private val defaultState = flower.defaultState

    abstract fun getGenerationPos(world: World, rand: Random, chunkPos: ChunkPos): BlockPos

    abstract val generationChance: Double
    abstract val patchGenerationAttempts: Int
    abstract val flowerGenerationAttempts: Int

    abstract fun canGenerateInWorld(world: World): Boolean

    override fun generate(rand: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        val biome = WorldGenUtil.getBiomeInChunk(world, chunkX, chunkZ)
        if (flower.isBiomeValid(biome) && canGenerateInWorld(world)) {
            val chunkPos = world.getChunk(chunkX, chunkZ).pos

            if (rand.nextDouble() < generationChance) {
                for (i in 0..patchGenerationAttempts) {
                    val pos = getGenerationPos(world, rand, chunkPos)
                    generateFlowers(world, rand, pos);
                }
            }

        }
    }

    private fun generateFlowers(world: World, rand: Random, pos: BlockPos) {
        for (i in 0 until flowerGenerationAttempts) {
            val blockPos = pos.add(
                rand.nextInt(8) - rand.nextInt(8),
                rand.nextInt(4) - rand.nextInt(4),
                rand.nextInt(8) - rand.nextInt(8)
            )

            if (world.isAirBlock(blockPos)
                    && blockPos.y < 255
                    && flower.canBlockStay(world, blockPos, defaultState)) {
                world.setBlockState(blockPos, defaultState, 2)
            }
        }
    }
}