package azmalent.backportedflora.common.block.flower

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.BackportedFlora
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Blocks
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary

class BlockWitherRose : AbstractFlower(NAME, ModConfig.WitherRose) {
    companion object {
        const val NAME = "wither_rose"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    override fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean {
        val soil = worldIn.getBlockState(pos.down()).block
        return soil == Blocks.SOUL_SAND || soil == Blocks.NETHERRACK
    }

    override fun isBiomeValid(biome: Biome): Boolean {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)
    }

    override fun onEntityCollision(worldIn: World, pos: BlockPos, state: IBlockState, entityIn: Entity) {
        if (ModConfig.WitherRose.causesWither && entityIn is EntityLivingBase) {
            entityIn.addPotionEffect(PotionEffect(MobEffects.WITHER, 120))
            entityIn.attackEntityFrom(DamageSource.WITHER, 1.0f)
        }
    }
}