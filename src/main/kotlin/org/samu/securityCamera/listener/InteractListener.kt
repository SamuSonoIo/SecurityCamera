package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.event.player.PlayerInteractEvent
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
        if (!PlayerCache.isInCreatingMode(player.uniqueId)) return@event
        if (clickedBlock == null) return@event
        if (PlayerCache.isWatching(player)) isCancelled = true

        val location = clickedBlock?.location?.clone()?.apply { y -= 2 }!!
        val playerData = PlayerCache.creatingMode[player.uniqueId]
        val name = playerData?.keys?.firstOrNull()
        val permission = playerData?.values?.firstOrNull()

        SecurityCamera.cameraManager.createCamera(name!!,location.x,location.y, location.z, location.world.name, permission!!, true)
        PlayerCache.removeCreatingMode(player.uniqueId)
        player.sendMessage(ConfigManager.readString("messages.created"))
    }

}