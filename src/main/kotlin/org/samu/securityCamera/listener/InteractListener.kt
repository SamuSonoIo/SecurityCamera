package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.ConfigManager
import org.samu.securityCamera.manager.cache.PlayerCache


class InteractListener {

    /**
     * Here we check if the player is in creating mode, if it is
     * we get the location he clicked on and create the camera there,
     * then we remove him from the PlayerCache hashmap.
     */
    val interact = event<PlayerInteractEvent> {
        if (clickedBlock == null) return@event

        if (PlayerCache.isWatching(player)) {
            isCancelled = true
            return@event
        }

        if (PlayerCache.isInCreatingMode(player.uniqueId)) {
            val location = clickedBlock?.location?.clone()?.apply { y -= 2 }!!
            location.yaw = (player.location.yaw * -1)
            val playerData = PlayerCache.creatingMode[player.uniqueId]
            val name = playerData?.keys?.firstOrNull()
            val permission = playerData?.values?.firstOrNull()

            SecurityCamera.cameraManager.createCamera(name!!,location.x,location.y, location.z, location.yaw, location.pitch,location.world.name, permission!!, true)
            PlayerCache.removeCreatingMode(player.uniqueId)
            player.sendMessage(ConfigManager.readString("messages.created"))
        }

        if (clickedBlock is ArmorStand) {
            val armorStand =  clickedBlock as ArmorStand
            println("This is an armor stand!")
            val customModelDataKey = NamespacedKey(SecurityCamera.INSTANCE, "security_camera")

            if (armorStand.persistentDataContainer.has(customModelDataKey, PersistentDataType.INTEGER)) isCancelled = true
        }
    }

}