package azmalent.backportedflora.client

import azmalent.backportedflora.common.registry.ModSoundEvents
import net.minecraft.block.SoundType

object ModSoundTypes {
    val SEAWEED = SoundType(
        1f, 1f,
        ModSoundEvents.SEAWEED_BREAK,
        ModSoundEvents.SEAWEED_STEP,
        ModSoundEvents.SEAWEED_PLACE,
        ModSoundEvents.SEAWEED_HIT,
        ModSoundEvents.SEAWEED_FALL
    )
}