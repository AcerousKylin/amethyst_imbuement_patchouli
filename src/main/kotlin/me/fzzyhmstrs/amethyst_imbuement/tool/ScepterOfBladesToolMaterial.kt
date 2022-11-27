package me.fzzyhmstrs.amethyst_imbuement.tool

import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterToolMaterial
import me.fzzyhmstrs.amethyst_imbuement.config.AiConfig
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import kotlin.math.max


object ScepterOfBladesToolMaterial: ScepterToolMaterial() {
    override fun getDurability(): Int {
        return AiConfig.items.bladesDurability
    }
    fun defaultDurability(): Int{
        return 500
    }
    override fun getMiningSpeedMultiplier(): Float {
        return 1.0f
    }
    override fun getAttackDamage(): Float {
        return AiConfig.items.bladesDamage
    }
    fun defaultAttackDamage(): Float {
        return 5.0f
    }
    override fun getAttackSpeed(): Double {
        return -3.0
    }
    override fun getMiningLevel(): Int {
        return 1
    }
    override fun getEnchantability(): Int {
        return 25
    }

    override fun getRepairIngredient(): Ingredient {
        return Ingredient.ofItems(Items.GOLD_INGOT)
    }
    override fun healCooldown(): Long {
        return max(AiConfig.items.baseRegenRateTicks - 25L,minCooldown())
    }
    override fun scepterTier(): Int{
        return 2
    }
}
