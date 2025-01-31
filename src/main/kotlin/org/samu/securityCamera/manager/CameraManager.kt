package org.samu.securityCamera.manager

import gg.flyte.twilight.extension.freeze
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache

data class Camera(
    val id: Int,
    val name: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val world: String,
    val permission: String = ""
)

class CameraManager {
    /**
     * This is the method to create the actual camera
     * this will call all the other methods needed and will help us
     * manage our data.
     *
     * @param name Name of the camera, cannot be duplicate
     * @param x X coordinate of the camera location
     * @param y Y coordinate of the camera location
     * @param z Z coordinate of the camera location
     * @param world World name where the camera is located
     * @param permission Permission of the camera, optional!
     * @param shouldWrite Should write the camera in the Json?
     */
    fun createCamera(
        name: String,
        x: Double,
        y: Double,
        z: Double,
        world: String,
        permission: String = "",
        shouldWrite: Boolean = false
    ): Camera? {
        val id = CameraCache.cameras.size + 1
        val camera = Camera(id, name, x, y, z, world, permission)
        val entity = this.createEntity(camera)

        if (entity == null) {
            Bukkit.getLogger().warning("Failed to create entity for camera: $name")
            return null
        }

        CameraCache.addCamera(camera, entity)
        if (shouldWrite) FileManager.writeData(camera)

        return camera
    }

    /**
     * Deletes the camera completely without
     * the need of a restart, it removes it
     * from the CameraCache and also from the
     * JSON Directly, it's used in the
     * RemoveCommand class.
     */
    fun deleteCamera(name: String) {
        FileManager.removeData(name)
        val cameraToRemove = CameraCache.cameras.keys.find { it.name == name }
        cameraToRemove?.let { camera ->
            CameraCache.cameras[camera]?.remove()
            CameraCache.cameras.remove(camera)
        }
    }

    /**
     * Will load the camera from the JSON
     * and use the method above to create them in the world.
     */
    fun loadCameras() {
        val cameras = FileManager.readData()

        cameras.forEach { camera ->
            createCamera(camera.name, camera.x, camera.y, camera.z, camera.world, camera.permission, false)
        }

        Bukkit.getLogger().info("Loaded cameras: ${cameras.size}")
    }

    /**
     * Teleport the player to the location provided
     * (Usually the location of the camera itself)
     * then we set his GameMode to Spectator so he
     * doesn't see his health, and he doesn't fall.
     *
     * @param sender Player we want to teleport.
     */
    fun watchCamera(sender: Player, cameraId: Int) {
        val cameraEntry = CameraCache.cameras.entries.find { it.key.id == cameraId }!!
        val camera = cameraEntry.key
        val entity = cameraEntry.value

        PlayerCache.addWatching(sender, camera)
        sender.gameMode = GameMode.SPECTATOR
        sender.teleport(Location(Bukkit.getWorld(camera.world), camera.x, camera.y, camera.z))

        entity.addPassenger(sender)
    }

    /**
     * Stop the player from watching whatever
     * camera he is in, this is a method
     * since it's called in DismountEvent
     * and in the CameraCommand.
     *
     * @param sender Player you want to remove from the camera.
     */
    fun stopWatching(sender: Player) {
        val watchingCameraData = PlayerCache.watchingCams[sender.uniqueId]
        val location = watchingCameraData!!.oldLocation
        val camera = watchingCameraData.camera

        sender.gameMode = GameMode.SURVIVAL

        val cameraEntry = CameraCache.cameras.entries.find { it.key.id == camera.id }
        cameraEntry?.value?.removePassenger(sender)

        sender.teleport(location)

        PlayerCache.removeWatching(sender)
    }

    /**
     * Creates an Armor Stand entity
     * and set his custom model data to a new one
     * so we can see the custom model in game.
     *
     * @param camera Instance of the camera you want to create the armor stand to.
     */
    private fun createEntity(camera: Camera): ArmorStand? {
        val world = Bukkit.getWorld(camera.world)
        if (world == null) {
            Bukkit.getLogger().warning("Could not find world: ${camera.world} for camera: ${camera.name}")
            return null
        }

        val location = Location(world, camera.x, camera.y, camera.z)

        val entity = world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand

        entity.setGravity(false)
        entity.freeze()
        val customModelDataKey = NamespacedKey(SecurityCamera.INSTANCE, "security_camera")

        entity.persistentDataContainer.set(customModelDataKey, PersistentDataType.INTEGER, camera.id)

        return entity
    }

    /**
     * Let's remove all entities from the world
     * to avoid them to duplicate on restart
     */
    fun clearEntities() = CameraCache.cameras.values.forEach { it.remove() }
}