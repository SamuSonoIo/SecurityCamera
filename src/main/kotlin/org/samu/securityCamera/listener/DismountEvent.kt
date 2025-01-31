package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.entity.Player
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.cache.PlayerCache
import org.spigotmc.event.entity.EntityDismountEvent

class DismountEvent {
    val dismountEvent = event<EntityDismountEvent> {
        if (entity !is Player) return@event

        val player = entity as Player
        if (PlayerCache.isWatching(player)) SecurityCamera.cameraManager.stopWatching(player)
    }
}