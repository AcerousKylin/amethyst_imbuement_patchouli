package me.fzzyhmstrs.amethyst_imbuement.item.armor

import me.fzzyhmstrs.amethyst_core.modifier_util.ModifierHelper
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterModifier
import me.fzzyhmstrs.fzzy_core.interfaces.Modifiable
import me.fzzyhmstrs.fzzy_core.item_util.FlavorHelper
import me.fzzyhmstrs.fzzy_core.modifier_util.ModifierHelperType
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.world.World

class SoulwovenArmorItem(material: ArmorMaterial, type: Type, settings: Settings) : ArmorItem(material, type, settings), Modifiable {

    private val attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier> by lazy {
        val map: ArrayListMultimap<EntityAttribute, EntityAttributeModifier> = ArrayListMultimap.create()
        map.putAll(super.getAttributeModifiers(type.equipmentSlot))
        when(type) {
            Type.BOOTS -> SpChecker.addSpellPowerAttribute("spell_power:soul","6959f0e8-7d8c-11ee-b962-0242ac120002",2.0, EntityAttributeModifier.Operation.ADDITION, map)
            Type.LEGGINGS -> SpChecker.addSpellPowerAttribute("spell_power:soul","6959f3ea-7d8c-11ee-b962-0242ac120002",2.0, EntityAttributeModifier.Operation.ADDITION, map)
            Type.CHESTPLATE -> SpChecker.addSpellPowerAttribute("spell_power:soul","6959f53e-7d8c-11ee-b962-0242ac120002",2.0, EntityAttributeModifier.Operation.ADDITION, map)
            Type.HELMET -> SpChecker.addSpellPowerAttribute("spell_power:soul","6959fab6-7d8c-11ee-b962-0242ac120002",2.0, EntityAttributeModifier.Operation.ADDITION, map)
        }
        map
    }
    
    private val flavorText: MutableText by lazy{
        FlavorHelper.makeFlavorText(this)
    }

    private val flavorTextDesc: MutableText by lazy{
        FlavorHelper.makeFlavorTextDesc(this)
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)
        FlavorHelper.addFlavorText(tooltip, context, flavorText, flavorTextDesc)
    }

    override fun defaultModifiers(type: ModifierHelperType<*>?): MutableList<Identifier> {
        if (type == ModifierHelper.getType())
            return mutableListOf(RegisterModifier.ensouledModifiers.random().modifierId)
        return super.defaultModifiers(type)
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier>{
        if (slot == type.equipmentSlot) {
                return this.attributeModifiers
        }
        return super.getAttributeModifiers(slot)
    }
}
