package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.cache.PlayerCache

class SneakEvent {
    val sneakEvent = event<PlayerToggleSneakEvent> {
        if (PlayerCache.isWatching(player)) SecurityCamera.cameraManager.stopWatching(player)
    }
}