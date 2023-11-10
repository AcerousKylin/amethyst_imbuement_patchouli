package me.fzzyhmstrs.amethyst_imbuement.entity.block

import me.fzzyhmstrs.amethyst_core.nbt_util.NbtKeys
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEntity
import me.fzzyhmstrs.fzzy_core.coding_util.AcText
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.util.Nameable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World


@Suppress("UNUSED_PARAMETER")
class AltarOfExperienceBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(RegisterEntity.ALTAR_OF_EXPERIENCE_BLOCK_ENTITY,pos, state),Nameable {

    var ticks = 0
    private var customName: Text? = null
    private var xpStored = intArrayOf(0,0)
    private var xpMax = intArrayOf(0,0)

    val storedXp: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return xpStored[index]
        }

        override fun set(index: Int, value: Int) {
            xpStored[index] = value
            markDirty()
        }

        //this is supposed to return the amount of integers you have in your delegate, in our example only one
        override fun size(): Int {
            return 2
        }
    }

    val maxXp: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return xpMax[index]
        }

        override fun set(index: Int, value: Int) {
            xpMax[index] = value
            markDirty()
        }

        //this is supposed to return the amount of integers you have in your delegate, in our example only one
        override fun size(): Int {
            return 2
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        if (hasCustomName()) {
            nbt.putString("CustomName", Text.Serializer.toJson(customName))
        }
        nbt.putInt(NbtKeys.ALTAR_KEY.str(),xpStored[0])
        nbt.putInt("altar_used_1",xpStored[1])
        nbt.putInt("max_xp",xpMax[0])
        nbt.putInt("max_xp_1",xpMax[1])
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        if (nbt.contains("CustomName", 8)) {
            customName = Text.Serializer.fromJson(nbt.getString("CustomName"))
        }
        if (nbt.contains(NbtKeys.ALTAR_KEY.str())) {
            xpStored[0] = nbt.getInt(NbtKeys.ALTAR_KEY.str())
            xpStored[1] = nbt.getInt("altar_used_1")
            xpMax[0] = nbt.getInt("max_xp")
            xpMax[1] = nbt.getInt("max_xp_1")
        }
    }

        var field_11964 = 0f
        var field_11963 = 0f
        var field_11962 = 0f

        companion object {
            fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: AltarOfExperienceBlockEntity) {
                //println("ticking")
                blockEntity.field_11963 = blockEntity.field_11964
                val playerEntity =
                    world.getClosestPlayer(pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5, 3.0, false)
                if (playerEntity != null) {
                    val d2 = playerEntity.x - (pos.x.toDouble() + 0.5)
                    val e = playerEntity.z - (pos.z.toDouble() + 0.5)
                    blockEntity.field_11962 = MathHelper.atan2(e, d2).toFloat()
                } else {
                    blockEntity.field_11962 += 0.02f
                }
                while (blockEntity.field_11964 >= Math.PI.toFloat()) {
                    blockEntity.field_11964 -= Math.PI.toFloat() * 2
                }
                while (blockEntity.field_11964 < (-Math.PI).toFloat()) {
                    blockEntity.field_11964 += Math.PI.toFloat() * 2
                }
                while (blockEntity.field_11962 >= Math.PI.toFloat()) {
                    blockEntity.field_11962 -= Math.PI.toFloat() * 2
                }
                while (blockEntity.field_11962 < (-Math.PI).toFloat()) {
                    blockEntity.field_11962 += Math.PI.toFloat() * 2
                }
                var d: Float = blockEntity.field_11962 - blockEntity.field_11964
                while (d >= Math.PI.toFloat()) {
                    d -= Math.PI.toFloat() * 2
                }
                while (d < (-Math.PI).toFloat()) {
                    d += Math.PI.toFloat() * 2
                }
                blockEntity.field_11964 += d * 0.4f
                blockEntity.ticks++
                if (blockEntity.ticks >= 360){
                    blockEntity.ticks = 0
                }
            }
        }


    override fun getName(): Text? {
        return if (customName != null) {
            customName
        } else AcText.translatable("container.altar_of_experience")
    }

    fun setCustomName(value: Text?) {
        customName = value
    }

    override fun getCustomName(): Text? {
        return customName
    }
}