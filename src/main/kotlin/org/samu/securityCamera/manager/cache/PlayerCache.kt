package org.samu.securityCamera.manager.cache

import org.bukkit.Location
import org.bukkit.entity.Player
import org.samu.securityCamera.manager.Camera
import java.util.UUID

data class WatchingCameraData(
    val oldLocation: Location,
    val camera: Camera,
)

object PlayerCache {

    /**
     * Creating mode, you get in the HashMap when you execute the command
     * with the correct params syntax and rules, you get removed
     * once you placed the camera, or you have executed the command again!
     */
    val creatingMode: MutableMap<UUID, MutableMap<String, String>> = mutableMapOf()

    fun addCreatingMode(uuid: UUID, name: String, permission: String) = creatingMode.put(uuid, mutableMapOf(name to permission))
    fun removeCreatingMode(uuid: UUID) = creatingMode.remove(uuid)
    fun isInCreatingMode(uuid: UUID) = creatingMode.contains(uuid)

    /**
     * List of players watching the cameras
     * it's a Key Value pair so we can see what camera a player is watching,
     * this is useful to get the location and the camera.
     */
    val watchingCams: MutableMap<UUID, WatchingCameraData> = mutableMapOf()

    fun addWatching(player: Player, camera: Camera) = watchingCams.put(player.uniqueId, WatchingCameraData(player.location, camera))
    fun removeWatching(player: Player) = watchingCams.remove(player.uniqueId)
    fun isWatching(player: Player):Boolean = watchingCams.containsKey(player.uniqueId)
}