package azmalent.backportedflora.common.block.seaweed

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockLiquid
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.common.EnumPlantType
import java.util.*
import kotlin.math.min

class BlockKelp : AbstractSeaweed(NAME) {
    companion object {
        const val NAME = "kelp"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        const val MAX_AGE = 15;

        val IS_TOP_BLOCK: PropertyBool = PropertyBool.create("top")
        val AGE: PropertyInteger = PropertyInteger.create("remaining_height", 0, MAX_AGE)
    }

    init {
        defaultState = blockState.baseState
                .withProperty(IS_TOP_BLOCK, false)
                .withProperty(AGE, 0)
                .withProperty(BlockLiquid.LEVEL, 15)

        tickRandomly = ModConfig.Kelp.growthEnabled
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(AGE, meta)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(AGE)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, IS_TOP_BLOCK, AGE, BlockLiquid.LEVEL)
    }

    override fun getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState {
        val hasKelpAbove = worldIn.getBlockState(pos.up()).block == this
        return state.withProperty(IS_TOP_BLOCK, !hasKelpAbove)
    }

    //Block behavior
    override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState {
        val down = world.getBlockState(pos.down())
        val age = if (down.block == this) {
            min(down.getValue(AGE) + 1, MAX_AGE)
        } else {
            Random().nextInt(MAX_AGE / 2);
        }

        return defaultState.withProperty(AGE, age)
    }


    override fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean {
        //Must have water above
        val up = worldIn.getBlockState(pos.up())
        if (up.material != Material.WATER) return false

        //Must have kelp or valid soil below
        val down = worldIn.getBlockState(pos.down())
        return down.block == this || down.material in ALLOWED_SOILS
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        //Try to grow if possible
        val age = state.getValue(AGE)
        if (age < MAX_AGE && rand.nextDouble() < 0.14) {
            val up = worldIn.getBlockState(pos.up())
            if (up.block == Blocks.WATER) {
                val newBlockState = defaultState.withProperty(AGE, age + 1)
                if (canBlockStay(worldIn, pos.up(), newBlockState)) {
                    worldIn.setBlockState(pos.up(), newBlockState)
                }
            }
        }
    }

    fun isBiomeValid(biome: Biome): Boolean {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)
    }

    private fun getTopPosition(worldIn: World, pos: BlockPos): BlockPos {
        var topPos = pos
        while(worldIn.getBlockState(topPos.up()).block == this) {
            topPos = topPos.up()
        }

        return topPos
    }

    // IGrowable implementation
    override fun canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean {
        val topPos = getTopPosition(worldIn, pos)
        val topAge = worldIn.getBlockState(topPos).getValue(AGE)

        return topAge < MAX_AGE && canBlockStay(worldIn, topPos.up(), state)
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState) {
        val topPos = getTopPosition(worldIn, pos)
        val topAge = worldIn.getBlockState(topPos).getValue(AGE)

        val newBlockState = defaultState.withProperty(AGE, topAge + 1)
        worldIn.setBlockState(topPos.up(), newBlockState)
    }
}