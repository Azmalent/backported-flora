package azmalent.backportedflora.common.util

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import java.util.*

object WorldGenUtil {
    fun getBiomeInChunk(world: World, chunkX: Int, chunkZ: Int): Biome {
        return world.getBiomeForCoordsBody(BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8))
    }
}