package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.Location
import org.bukkit.event.player.PlayerMoveEvent
import org.samu.securityCamera.manager.cache.PlayerCache

class PlayerMove  {
    val move = event<PlayerMoveEvent> {
        if (PlayerCache.isWatching(player)) {
            if (from.x != to.x || from.y != to.y || from.z != to.z) {
                isCancelled = true
                val location = Location(
                    from.world,
                    from.x,
                    from.y,
                    from.z,
                    to.yaw,
                    to.pitch
                )
                player.teleport(location)
            }
        }
    }
}
