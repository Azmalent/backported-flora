package azmalent.backportedflora.common.util

import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome

object WorldGenUtil {
    fun getBiomeInChunk(world: World, chunkX: Int, chunkZ: Int): Biome {
        return world.getBiomeForCoordsBody(BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8))
    }

    fun canSeeSky(world: World, pos: BlockPos): Boolean {
        var topPos = pos
        while (world.getBlockState(topPos).block == Blocks.WATER) {
            topPos = topPos.up()
        }

        val block = world.getBlockState(topPos)
        if (world.isAirBlock(topPos) || block == Blocks.ICE || block == Blocks.WATERLILY) {
            return world.canSeeSky(topPos)
        }

        return false
    }
}