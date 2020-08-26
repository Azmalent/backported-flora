package azmalent.backportedflora.common.world

import azmalent.backportedflora.common.block.flower.AbstractFlower
import azmalent.backportedflora.common.util.WorldGenUtil
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import java.util.*

class WorldGenOverworldFlowers(flower: AbstractFlower) :  WorldGenCustomFlowers(flower) {
    override fun getGenerationPos(world: World, rand: Random, chunkPos: ChunkPos): BlockPos {
        val x = rand.nextInt(16) + 8
        val z = rand.nextInt(16) + 8

        val yRange = world.getHeight(chunkPos.getBlock(0, 0, 0).add(x, 0, z)).y + 32
        val y = rand.nextInt(yRange)

        return chunkPos.getBlock(0, 0, 0).add(x, y, z)
    }

    override val generationChance = 0.125
    override val patchGenerationAttempts = 4
    override val flowerGenerationAttempts = 64

    override fun canGenerateInWorld(world: World): Boolean {
        return world.worldType != WorldType.FLAT
    }
}