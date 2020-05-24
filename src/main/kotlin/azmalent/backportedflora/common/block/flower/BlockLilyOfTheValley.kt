package azmalent.backportedflora.common.block.flower

import azmalent.backportedflora.BackportedFlora
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary

class BlockLilyOfTheValley : AbstractFlower(NAME) {
    companion object {
        const val NAME = "lily_of_the_valley"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    override fun isBiomeValid(biome: Biome): Boolean {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) ||
                BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE)
    }
}