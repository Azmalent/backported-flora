package azmalent.backportedflora.common.block.seaweed

import azmalent.backportedflora.BackportedFlora
import net.minecraft.block.BlockLiquid
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.util.IStringSerializable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockSeagrass : AbstractSeaweed(NAME) {
    enum class SeagrassVariant : IStringSerializable {
        SINGLE, BOTTOM, TOP;

        override fun getName(): String {
            return name.toLowerCase()
        }

        override fun toString(): String {
            return getName()
        }
    }

    companion object {
        const val NAME = "seagrass"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        val VARIANT: PropertyEnum<SeagrassVariant> = PropertyEnum.create("variant", SeagrassVariant::class.java)
    }

    init {
        defaultState = blockState.baseState
                .withProperty(VARIANT, SeagrassVariant.SINGLE)
                .withProperty(BlockLiquid.LEVEL, 15)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return 0
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, VARIANT, BlockLiquid.LEVEL)
    }

    override fun getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState {
        val hasSeagrassBelow = worldIn.getBlockState(pos.down()).block == this
        val hasSeagrassAbove = worldIn.getBlockState(pos.up()).block == this

        return state.withProperty(VARIANT, when {
            hasSeagrassBelow -> SeagrassVariant.TOP
            hasSeagrassAbove -> SeagrassVariant.BOTTOM
            else -> SeagrassVariant.SINGLE
        })
    }

    @SideOnly(Side.CLIENT)
    override fun getOffsetType(): EnumOffsetType {
        return EnumOffsetType.XZ
    }

    override fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean {
        //Must have water above
        val up = worldIn.getBlockState(pos.up())

        if (up.material != Material.WATER) return false

        //Must have a SINGLE seagrass or valid soil below
        val down = worldIn.getBlockState(pos.down())
        if (down.block == this) {
            return worldIn.getBlockState(pos.down(2)).block != this
        }

        return down.material in ALLOWED_SOILS
    }
}