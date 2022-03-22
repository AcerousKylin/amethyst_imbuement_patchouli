package me.fzzyhmstrs.amethyst_imbuement.scepter

import me.fzzyhmstrs.amethyst_imbuement.entity.CrystallineGolemEntity
import me.fzzyhmstrs.amethyst_imbuement.item.ImbuedJewelryItem
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEntity
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterItem
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.SummonEntityAugment
import me.fzzyhmstrs.amethyst_imbuement.util.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.passive.ChickenEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World

class SummonGolemAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SummonEntityAugment(tier, maxLvl, *slot) {
    override fun placeEntity(world: World, user: PlayerEntity, hit: HitResult, level: Int): Boolean {

        val xrnd: Double = (hit as BlockHitResult).blockPos.x + (world.random.nextDouble() * 4.0 - 2.0)
        val zrnd: Double = hit.blockPos.z + (world.random.nextDouble() * 4.0 - 2.0)
        val yrnd = hit.blockPos.y + 1.0
        val golem = CrystallineGolemEntity(RegisterEntity.CRYSTAL_GOLEM_ENTITY, world,6000)
        golem.setPos(xrnd, yrnd, zrnd)
        world.spawnEntity(golem)

        world.playSound(null,user.blockPos,soundEvent(), SoundCategory.PLAYERS,1.0F,1.0F)
        return true
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ENTITY_IRON_GOLEM_REPAIR
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.WIT,6000,250,25,imbueLevel,2, Items.AMETHYST_BLOCK)
    }
}