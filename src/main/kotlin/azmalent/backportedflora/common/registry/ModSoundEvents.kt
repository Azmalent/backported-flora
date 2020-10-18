package azmalent.backportedflora.common.registry

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.ModConfig
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.registries.IForgeRegistry

object ModSoundEvents {
    private fun createSoundEvent(path: String): SoundEvent {
        val location = ResourceLocation(BackportedFlora.MODID, path)

        return SoundEvent(location).apply {
            registryName = location
        }
    }

    val SEAWEED_BREAK = createSoundEvent("block.seaweed.break")
    val SEAWEED_STEP  = createSoundEvent("block.seaweed.footsteps")
    val SEAWEED_PLACE = createSoundEvent("block.seaweed.place")
    val SEAWEED_HIT   = createSoundEvent("block.seaweed.hit")
    val SEAWEED_FALL  = createSoundEvent("block.seaweed.fall")

    fun register(registry: IForgeRegistry<SoundEvent>) {
        if (ModConfig.Seagrass.enabled || ModConfig.Kelp.enabled) {
            registry.registerAll(
                SEAWEED_BREAK,
                SEAWEED_STEP,
                SEAWEED_PLACE,
                SEAWEED_HIT,
                SEAWEED_FALL
            )
        }
    }
}