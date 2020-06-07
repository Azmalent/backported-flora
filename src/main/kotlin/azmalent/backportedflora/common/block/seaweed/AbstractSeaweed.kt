package azmalent.backportedflora.common.block.seaweed

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.client.ModSoundTypes
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class AbstractSeaweed(name: String) : Block(Material.WATER, MapColor.WATER), IPlantable {
    companion object {
        val ALLOWED_SOILS = setOf<Material>(
                Material.GROUND, Material.SAND, Material.GRASS, Material.CLAY, Material.ROCK
        )
    }

    lateinit var itemBlock: Item

    init {
        setRegistryName(name)
        translationKey = name
        soundType = ModSoundTypes.SEAWEED
        creativeTab = BackportedFlora.creativeTab
    }

    fun createItemBlock(): Item {
        itemBlock = ItemBlock(this).setRegistryName(registryName).setTranslationKey(translationKey)
        return itemBlock
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemBlockRenderer(itemBlock, 0, registryName.toString())
    }



    override fun getCollisionBoundingBox(state: IBlockState, world: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        return NULL_AABB
    }

    //Rendering
    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT
    }

    override fun isFullCube(state: IBlockState): Boolean {
        return false
    }

    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false
    }

    //Block behavior
    abstract fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean

    private fun checkAndDropBlock(world: IBlockAccess, pos: BlockPos, state: IBlockState) {
        if (!canBlockStay(world as World, pos, state)) {
            dropBlockAsItem(world, pos, state, 0)
            world.setBlockState(pos, Blocks.WATER.defaultState, 3)
        }
    }

    override fun isReplaceable(world: IBlockAccess, pos: BlockPos): Boolean {
        return false
    }

    override fun canPlaceBlockOnSide(worldIn: World, pos: BlockPos, side: EnumFacing): Boolean {
        return canBlockStay(worldIn, pos, defaultState)
    }

    override fun canPlaceBlockAt(worldIn: World, pos: BlockPos): Boolean {
        return canBlockStay(worldIn, pos, defaultState)
    }


    override fun neighborChanged(state: IBlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos) {
        checkAndDropBlock(worldIn, pos, state)
    }

    //Leave water when broken
    override fun onPlayerDestroy(worldIn: World, pos: BlockPos, state: IBlockState) {
        worldIn.setBlockState(pos, Blocks.WATER.defaultState, 3)
    }

    //IPlantable implementation
    override fun getPlantType(world: IBlockAccess, pos: BlockPos): EnumPlantType {
        return EnumPlantType.Water
    }

    override fun getPlant(world: IBlockAccess, pos: BlockPos): IBlockState {
        return world.getBlockState(pos)
    }
}