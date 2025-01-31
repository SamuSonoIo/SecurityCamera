package org.samu.securityCamera.manager.cache

import org.bukkit.entity.ArmorStand
import org.samu.securityCamera.manager.Camera

object CameraCache {
    /**
     * Maps of available cameras.
     * All cameras stored in the JSON will be stored here,
     * this is to help manage data and to don't read data from JSON every time
     * which can lead to performance issues.
     * The Armor Stand is the entity where the player
     * will sit on.
     */
    val cameras: MutableMap<Camera, ArmorStand> = mutableMapOf()

    fun addCamera(camera: Camera, armorStand: ArmorStand) { cameras[camera] = armorStand }
    fun removeCamera(cameraId: Int) = cameras.remove(cameras.keys.first { it.id == cameraId })
}