package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.event.player.PlayerQuitEvent
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.cache.PlayerCache

class QuitEvent  {
    val quit = event<PlayerQuitEvent> {
        if (PlayerCache.isWatching(player)) {
            SecurityCamera.cameraManager.stopWatching(player)
            return@event
        }

        if (PlayerCache.isInCreatingMode(player.uniqueId)) PlayerCache.creatingMode.remove(player.uniqueId)
    }
}
