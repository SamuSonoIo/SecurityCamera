package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.gui.GUI
import org.samu.securityCamera.manager.config.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache

class GuiEvents {
    val click = event<InventoryClickEvent> {
        val player = whoClicked as Player

        if (currentItem == null || currentItem!!.type == Material.AIR || !currentItem!!.hasItemMeta() || inventory.isEmpty) return@event

        isCancelled = true

        val pdc = currentItem!!.itemMeta.persistentDataContainer
        val cameraIdKey = CameraCache.cameras.keys.map {
            NamespacedKey(SecurityCamera.INSTANCE, "camera-${it.id}")
        }.find { pdc.has(it, PersistentDataType.INTEGER) }

        if (cameraIdKey != null) {
            val cameraId = pdc.get(cameraIdKey, PersistentDataType.INTEGER) ?: return@event
            val camera = CameraCache.cameras.keys.find { it.id == cameraId }

            if (camera == null) return@event

            SecurityCamera.cameraManager.watchCamera(player, cameraId)
        }

        val pageKeyLeft = NamespacedKey(SecurityCamera.INSTANCE, "left-page")
        val pageKeyRight = NamespacedKey(SecurityCamera.INSTANCE, "right-page")

        when {
            currentItem!!.itemMeta.persistentDataContainer.has(pageKeyRight, PersistentDataType.INTEGER) -> {
                val nextPage = currentItem!!.itemMeta.persistentDataContainer.get(pageKeyRight, PersistentDataType.INTEGER)!!
                GUI(player, nextPage, ConfigManager.readInt("gui-length"))
            }
            currentItem!!.itemMeta.persistentDataContainer.has(pageKeyLeft, PersistentDataType.INTEGER) -> {
                val prevPage = currentItem!!.itemMeta.persistentDataContainer.get(pageKeyLeft, PersistentDataType.INTEGER)!!
                GUI(player, prevPage, ConfigManager.readInt("gui-length"))
            }
        }
    }
}
