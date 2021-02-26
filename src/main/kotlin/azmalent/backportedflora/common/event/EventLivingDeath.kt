package azmalent.backportedflora.common.event

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object EventLivingDeath {
    @SubscribeEvent
    @JvmStatic fun onEntityKilledByWither(event: LivingDeathEvent) {
        if (!ModConfig.WitherRose.enabled || !ModConfig.WitherRose.dropFromWitherKills) return

        val entity = event.entityLiving
        val world = entity.world

        if (!world.isRemote && entity.attackingEntity is EntityWither) {
            var placed = false

            if (ForgeEventFactory.getMobGriefingEvent(world, entity)) {
                val pos = entity.position
                if (world.isAirBlock(pos) && ModBlocks.WITHER_ROSE.canPlaceBlockAt(world, pos)) {
                    world.setBlockState(pos, ModBlocks.WITHER_ROSE.defaultState, 3)
                    placed = true
                }
            }

            if (!placed) {
                entity.entityDropItem(ItemStack(ModBlocks.WITHER_ROSE), 1f)
            }
        }
    }
}