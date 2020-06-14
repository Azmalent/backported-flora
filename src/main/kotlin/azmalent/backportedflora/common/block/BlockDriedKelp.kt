package azmalent.backportedflora.common.block

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.client.ModSoundTypes
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockDriedKelp : Block(Material.GRASS, MapColor.GRAY) {
    companion object {
        const val NAME = "dried_kelp_block"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    lateinit var itemBlock: Item

    init {
        setRegistryName(NAME)
        translationKey = NAME
        soundType = SoundType.PLANT
        creativeTab = BackportedFlora.creativeTab

        blockHardness = 0.5f
    }

    override fun isFlammable(world: IBlockAccess, pos: BlockPos, face: EnumFacing): Boolean {
        return true
    }

    override fun getFlammability(world: IBlockAccess, pos: BlockPos, face: EnumFacing): Int {
        return 30
    }

    override fun getHarvestTool(state: IBlockState): String? {
        return "hoe"
    }

    fun createItemBlock(): Item {
        itemBlock = ItemBlock(this).setRegistryName(registryName).setTranslationKey(translationKey)
        return itemBlock
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemBlockRenderer(itemBlock, 0, registryName.toString())
    }
}