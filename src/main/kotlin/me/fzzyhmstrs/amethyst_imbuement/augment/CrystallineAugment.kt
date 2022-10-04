package me.fzzyhmstrs.amethyst_imbuement.augment

import me.fzzyhmstrs.amethyst_core.trinket_util.base_augments.AbstractEquipmentAugment
import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.EquipmentAugment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.*
import net.minecraft.util.registry.Registry

class CrystallineAugment(weight: Rarity, mxLvl: Int = 1, vararg slot: EquipmentSlot): EquipmentAugment(weight, mxLvl, EnchantmentTarget.WEAPON, *slot) {

    override fun getAttackDamage(level: Int, group: EntityGroup?): Float {
        if (!enabled) return 0f
        return 0.25F * level
    }

    override fun isAcceptableItem(stack: ItemStack): Boolean {
        return (EnchantmentTarget.CROSSBOW.isAcceptableItem(stack.item) ||
                EnchantmentTarget.TRIDENT.isAcceptableItem(stack.item) ||
                EnchantmentTarget.BOW.isAcceptableItem(stack.item) ||
                (stack.item is AxeItem) ||
                EnchantmentTarget.WEAPON.isAcceptableItem(stack.item))
    }

    override fun acceptableItemStacks(): MutableList<ItemStack> {
        val list = mutableListOf<ItemStack>()
        val entries = Registry.ITEM.indexedEntries
        list.addAll(super.acceptableItemStacks().asIterable())
        for (entry in entries){
            val item = entry.value()
            if (item is AxeItem || item is CrossbowItem || item is TridentItem || item is BowItem){
                list.add(ItemStack(item,1))
            }
        }
        return list
    }
}