package org.samu.securityCamera.gui

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.samu.securityCamera.SecurityCamera

object PageUtil {
    fun pageItems(items: List<ItemStack>, page: Int, spaces: Int): List<ItemStack> {
        val availableSpaces = spaces - 2

        val upperBound = minOf(page * availableSpaces, items.size)
        val lowerBound = maxOf(upperBound - availableSpaces, 0)

        return items.subList(lowerBound, upperBound)
    }

    fun isPageValid(items: List<ItemStack>, page: Int, spaces: Int): Boolean {
        val availableSpaces = spaces - 2
        if (page <= 0) return false

        val upperBound = page * availableSpaces
        val lowerBound = upperBound - availableSpaces

        return items.size > lowerBound
    }

    fun createItem(material: Material, name: Component, description: List<Component>, pdc: String = "", pdcId: Int = 0, amount: Int = 1): ItemStack {
        val itemStack = ItemStack(material, amount)
        val meta = itemStack.itemMeta
        meta.displayName(name)
        meta.lore(description)
        if (pdc.isNotBlank()) {
            val key = NamespacedKey(SecurityCamera.INSTANCE, pdc)
            meta.persistentDataContainer.set(key, PersistentDataType.INTEGER, pdcId)
        }
        itemStack.itemMeta = meta
        return itemStack
    }
}